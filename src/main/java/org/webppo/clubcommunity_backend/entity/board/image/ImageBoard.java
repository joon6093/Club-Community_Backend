package org.webppo.clubcommunity_backend.entity.board.image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.webppo.clubcommunity_backend.dto.board.image.ImageBoardUpdateRequest;
import org.webppo.clubcommunity_backend.dto.board.image.ImageUpdatedResult;
import org.webppo.clubcommunity_backend.entity.board.Board;
import org.webppo.clubcommunity_backend.entity.club.Club;
import org.webppo.clubcommunity_backend.entity.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@DiscriminatorValue("image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageBoard extends Board {

    @OneToMany(mappedBy = "imageBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Image> images = new ArrayList<>();

    @Builder
    public ImageBoard(String title, Club club, Member member) {
        super(title, club, member);
    }

    public void addImages(List<Image> images) {
        images.forEach(image -> {
            this.images.add(image);
            image.setImageBoard(this);
        });
    }

    public ImageUpdatedResult update(ImageBoardUpdateRequest req) {
        ImageUpdatedResult result = findImageUpdatedResult(req.getAddedImages(), req.getDeletedImages());
        addImages(result.getAddedImages());
        removeImages(result.getDeletedImages());
        return result;
    }

    private ImageUpdatedResult findImageUpdatedResult(List<MultipartFile> addedImageFiles, List<Long> deletedImageIds) {
        List<Image> addedImages = convertImageFilesToImages(addedImageFiles);
        List<Image> deletedImages = convertImageIdsToImages(deletedImageIds);
        return new ImageUpdatedResult(addedImageFiles, addedImages, deletedImages);
    }

    private List<Image> convertImageFilesToImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(imageFile -> new Image(imageFile.getOriginalFilename())).collect(toList());
    }

    private List<Image> convertImageIdsToImages(List<Long> imageIds) {
        return imageIds.stream()
                .map(this::convertImageIdToImage)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    private Optional<Image> convertImageIdToImage(Long id) {
        return this.images.stream().filter(i -> i.getId().equals(id)).findAny();
    }

    public void removeImages(List<Image> imageList) {
        List<Image> imagesToRemove = new ArrayList<>(imageList);
        for (Image image : imagesToRemove) {
            this.images.remove(image);
            image.setImageBoard(null);
        }
    }
}

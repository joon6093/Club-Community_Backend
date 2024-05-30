package org.webppo.clubcommunity_backend.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.webppo.clubcommunity_backend.response.exception.board.UnsupportedFileFormatException;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@DiscriminatorColumn(name = "file_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    protected String uniqueName;

    @Column(nullable = false)
    protected String originName;

    protected static String[] supportedExtensions;

    public File(String originName) {
        this.uniqueName = generateUniqueName(extractExtension(originName));
        this.originName = originName;
    }

    protected String generateUniqueName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    protected String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if (isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) {
            throw new UnsupportedFileFormatException(e);
        }
        throw new UnsupportedFileFormatException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtensions).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}

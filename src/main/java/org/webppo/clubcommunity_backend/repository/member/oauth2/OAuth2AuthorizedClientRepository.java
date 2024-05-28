package org.webppo.clubcommunity_backend.repository.member.oauth2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.webppo.clubcommunity_backend.entity.member.oauth2.OAuth2AuthorizedClient;
import org.webppo.clubcommunity_backend.entity.member.oauth2.OAuth2AuthorizedClientId;

@Repository
public interface OAuth2AuthorizedClientRepository extends JpaRepository<OAuth2AuthorizedClient, OAuth2AuthorizedClientId> {

}

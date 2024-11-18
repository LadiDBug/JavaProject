package org.blackjack.api;

import java.util.List;

public record SetupPackage(PackageType packageType, List<String> playerNames, int numberOfPlayers,
                           List<String> avatars) implements DataPackage {

}

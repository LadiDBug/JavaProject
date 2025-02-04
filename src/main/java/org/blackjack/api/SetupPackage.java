package org.blackjack.api;

import java.util.List;

/**
 * SetupPackage class
 *
 * @param packageType
 * @param playerNames
 * @param numberOfPlayers
 * @param avatars
 */
public record SetupPackage(PackageType packageType, List<String> playerNames, int numberOfPlayers,
                           List<String> avatars) implements DataPackage {

}

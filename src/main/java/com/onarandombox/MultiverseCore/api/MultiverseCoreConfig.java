package com.onarandombox.MultiverseCore.api;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * The configuration of MultiverseCore.
 */
public interface MultiverseCoreConfig extends ConfigurationSerializable {
    /**
     * Sets a property using a {@link String}.
     *
     * @param property The name of the property.
     * @param value    The value.
     * @return True on success, false if the operation failed.
     */
    boolean setConfigProperty(String property, String value);

    /**
     * Gets portalCooldown.
     *
     * @return portalCooldown.
     */
    int getTeleportCooldown();

    /**
     * Sets portalCooldown.
     *
     * @param portalCooldown The new value.
     */
    void setTeleportCooldown(int portalCooldown);

    /**
     * Gets firstSpawnWorld.
     *
     * @return firstSpawnWorld.
     */
    String getFirstSpawnWorld();

    /**
     * Sets firstSpawnWorld.
     *
     * @param firstSpawnWorld The new value.
     */
    void setFirstSpawnWorld(String firstSpawnWorld);

    /**
     * Gets version.
     *
     * @return version.
     */
    double getVersion();

    /**
     * Sets version.
     *
     * @param version The new value.
     */
    void setVersion(int version);

    /**
     * Gets messageCooldown.
     *
     * @return messageCooldown.
     */
    int getMessageCooldown();

    /**
     * Sets messageCooldown.
     *
     * @param messageCooldown The new value.
     */
    void setMessageCooldown(int messageCooldown);

    /**
     * Gets globalDebug.
     *
     * @return globalDebug.
     */
    int getGlobalDebug();

    /**
     * Sets globalDebug.
     *
     * @param globalDebug The new value.
     */
    void setGlobalDebug(int globalDebug);

    /**
     * Gets displayPermErrors.
     *
     * @return displayPermErrors.
     */
    boolean getDisplayPermErrors();

    /**
     * Sets displayPermErrors.
     *
     * @param displayPermErrors The new value.
     */
    void setDisplayPermErrors(boolean displayPermErrors);

    /**
     * Gets firstSpawnOverride.
     *
     * @return firstSpawnOverride.
     */
    boolean getFirstSpawnOverride();

    /**
     * Sets firstSpawnOverride.
     *
     * @param firstSpawnOverride The new value.
     */
    void setFirstSpawnOverride(boolean firstSpawnOverride);

    /**
     * Gets teleportIntercept.
     *
     * @return teleportIntercept.
     */
    boolean getTeleportIntercept();

    /**
     * Sets teleportIntercept.
     *
     * @param teleportIntercept The new value.
     */
    void setTeleportIntercept(boolean teleportIntercept);

    /**
     * Gets prefixChat.
     *
     * @return prefixChat.
     */
    boolean getPrefixChat();

    /**
     * Sets prefixChat.
     *
     * @param prefixChat The new value.
     */
    void setPrefixChat(boolean prefixChat);

    /**
     * Gets prefixChatFormat.
     *
     * @return prefixChatFormat.
     */
    String getPrefixChatFormat();

    /**
     * Sets prefixChatFormat.
     *
     * @param prefixChatFormat The new value.
     */
    void setPrefixChatFormat(String prefixChatFormat);

    /**
     * Gets enforceAccess.
     *
     * @return enforceAccess.
     */
    boolean getEnforceAccess();

    /**
     * Sets enforceAccess.
     *
     * @param enforceAccess The new value.
     */
    void setEnforceAccess(boolean enforceAccess);

    /**
     * Gets useasyncchat.
     *
     * @return useasyncchat.
     */
    boolean getUseAsyncChat();

    /**
     * Sets useasyncchat.
     *
     * @param useAsyncChat The new value.
     */
    void setUseAsyncChat(boolean useAsyncChat);

    /**
     * Whether we are suppressing startup messages.
     *
     * @return true if we are suppressing startup messages.
     */
    boolean getSilentStart();

    /**
     * Sets whether to suppress startup messages.
     *
     * @param silentStart true to suppress messages.
     */
    void setSilentStart(boolean silentStart);

    /**
     * Sets whether or not to let Bukkit determine portal search radius on its own or if Multiverse should give input.
     *
     * @param useDefaultPortalSearch True to let Bukkit determine portal search radius on its own.
     */
    void setUseDefaultPortalSearch(boolean useDefaultPortalSearch);

    /**
     * Gets whether or not Bukkit will be determining portal search radius on its own or if Multiverse should help.
     *
     * @return True means Bukkit will use its own default values.
     */
    boolean isUsingDefaultPortalSearch();

    /**
     * Gets the radius at which vanilla style portals will be searched for to connect to worlds together.
     *
     * @return The portal search radius.
     */
    int getPortalSearchRadius();

    /**
     * Sets the radius at which vanilla style portals will be searched for to connect to worlds together.
     *
     * @param searchRadius The portal search radius.
     */
    void setPortalSearchRadius(int searchRadius);

    /**
     * Gets whether or not the automatic purge of entities is enabled.
     *
     * @return True if automatic purge is enabled.
     */
    boolean isAutoPurgeEnabled();

    /**
     * Sets whether or not the automatic purge of entities is enabled.
     *
     * @param autopurge True if automatic purge should be enabled.
     */
    void setAutoPurgeEnabled(boolean autopurge);
}

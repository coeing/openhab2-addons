/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.boschshc.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;

/**
 * The {@link BoschSHCHandler} represents Bosch Things. Each type of device inherits from this abstract thing handler.
 *
 * @author Stefan Kästle - Initial contribution
 */
@NonNullByDefault
public abstract class BoschSHCHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(BoschSHCHandler.class);
    private @Nullable BoschSHCConfiguration config;

    public BoschSHCHandler(Thing thing) {
        super(thing);
        logger.warn("Creating thing: {}", thing.getLabel());
    }

    public @Nullable String getBoschID() {
        if (this.config != null) {
            return this.config.id;
        } else {
            return null;
        }
    }

    public @Nullable BoschSHCConfiguration getBoschConfig() {
        return this.config;
    }

    @Override
    public void initialize() {

        this.config = getConfigAs(BoschSHCConfiguration.class);
        logger.warn("Initializing thing: {}", this.config.id);

        // Mark immediately as online - if the bridge is online, the thing is too.
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public abstract void handleCommand(ChannelUID channelUID, Command command);

    public abstract void processUpdate(String id, JsonElement state);

}
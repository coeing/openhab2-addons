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

import static org.openhab.binding.boschshc.internal.BoschSHCBindingConstants.*;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link BoschSHCHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * TODO Most of the things in here should actually be a Bridge?
 *
 * @author Stefan Kästle - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.boschshc", service = ThingHandlerFactory.class)
public class BoschSHCHandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(BoschSHCHandlerFactory.class);
    private @Nullable BoschSHCBridgeHandler bridge;

    // List of all supported Bosch devices.
    public static final Collection<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Arrays.asList(THING_TYPE_SHC,
            THING_TYPE_INWALL_SWITCH, THING_TYPE_TWINGUARD);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {

        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        logger.warn("Thing createHandler for thing: {} - {}", thing.getLabel(), thingTypeUID);

        // XXX Make the names in here consistent - remove the stupid Bosch prefixes.

        if (THING_TYPE_SHC.equals(thingTypeUID)) {
            return new BoschSHCBridgeHandler((Bridge) thing);
        }

        else if (THING_TYPE_INWALL_SWITCH.equals(thingTypeUID)) {
            return new BoschInWallSwitchHandler(thing);
        }

        else if (THING_TYPE_TWINGUARD.equals(thingTypeUID)) {
            return new BoschTwinguardHandler(thing);
        }

        else if (THING_TYPE_WINDOW_CONTACT.equals(thingTypeUID)) {
            return new WindowContactHandler(thing);
        }

        else {
            logger.warn("Failed to find handler for device: {}", thingTypeUID);
        }

        return null;
    }

}
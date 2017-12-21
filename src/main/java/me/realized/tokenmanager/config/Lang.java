/*
 *
 *   This file is part of TokenManager, licensed under the MIT License.
 *
 *   Copyright (c) Realized
 *   Copyright (c) contributors
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 *
 */

package me.realized.tokenmanager.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.realized.tokenmanager.TokenManagerPlugin;
import me.realized.tokenmanager.util.StringUtil;
import me.realized.tokenmanager.util.config.AbstractConfiguration;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;

public class Lang extends AbstractConfiguration<TokenManagerPlugin> {

    private final Map<String, String> messages = new HashMap<>();

    public Lang(final TokenManagerPlugin plugin) {
        super(plugin, "lang");
    }

    private static String withReplacers(String message, final Object... replacers) {
        for (int i = 0; i < replacers.length; i += 2) {
            if (i + 1 >= replacers.length) {
                break;
            }

            message = message.replace("%" + String.valueOf(replacers[i]) + "%", String.valueOf(replacers[i + 1]));
        }

        return message;
    }

    @Override
    public void handleLoad() throws IOException, InvalidConfigurationException {
        super.handleLoad();

        final Map<String, String> strings = new HashMap<>();

        for (final String key : getConfiguration().getKeys(true)) {
            final Object value = getConfiguration().get(key);

            if (value == null || value instanceof MemorySection) {
                continue;
            }

            String message = value instanceof List ? StringUtil.fromList((List<?>) value) : value.toString();

            // Loads the STRINGS section values by their last section key.
            if (key.startsWith("STRINGS")) {
                final String[] args = key.split(".");
                strings.put(args[args.length - 1], message);
            } else {
                messages.put(key, message);
            }

            // Replace any STRINGS in the message.
            for (final Map.Entry<String, String> entry : strings.entrySet()) {
                final String placeholder = "{" + entry.getKey() + "}";

                if (StringUtils.containsIgnoreCase(message, placeholder)) {
                    message = message.replaceAll("(?i)" + placeholder, entry.getValue());
                }
            }
        }
    }

    @Override
    public void handleUnload() {
        messages.clear();
    }

    public void sendMessage(final CommandSender receiver, final boolean config, final String in, final Object... replacers) {
        if (config) {
            final String message = messages.get(in);

            if (message == null) {
                getPlugin().getLogger().warning("Provided key '" + in + "' has no assigned value, cannot send message");
                return;
            }

            // todo: Add check for message length and send them separately if reached max chars
            receiver.sendMessage(StringUtil.color(withReplacers(message, replacers)));
        } else {
            receiver.sendMessage(StringUtil.color(withReplacers(in, replacers)));
        }
    }
}
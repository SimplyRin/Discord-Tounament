package net.simplyrin.discord.event;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;
import net.simplyrin.discord.event.listener.MessageListener;
import net.simplyrin.discord.event.utils.ModManager;
import net.simplyrin.discord.event.utils.UserManager;
import net.simplyrin.rinstream.RinStream;

/**
 * Created by SimplyRin on 2018/07/17.
 *
 * Copyright (C) 2018 SimplyRin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	@Getter
	private JDA jda;

	@Getter
	private Configuration config;

	@Getter
	private ModManager modManager;
	@Getter
	private UserManager userManager;

	private RinStream rinStream;

	public void run() {
		this.rinStream = new RinStream();

		System.out.println("Loading files...");

		File file = new File("config.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Configuration config = Config.getConfig(file);
			config.set("Token", "BOT_TOKEN_HERE");

			config.set("Owner", "224428706209202177");
			config.set("TournamentName", "Tounament Name #1");
			config.set("Welcome.Role", "User");
			config.set("Channels.Welcome", "000000000000000000");
			config.set("Channels.Application", "000000000000000000");
			config.set("Roles.Moderator", "Moderator");

			Config.saveConfig(config, file);
		}

		this.config = Config.getConfig(file);

		JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT);
		jdaBuilder.addEventListener(new MessageListener(this));

		String token = this.config.getString("Token");
		if (token.equals("BOT_TOKEN_HERE")) {
			System.out.println("Discord Bot Token を config.yml に入力してください！");
			System.exit(0);
			return;
		}
		jdaBuilder.setToken(token);

		this.jda = null;
		try {
			this.jda = jdaBuilder.buildBlocking();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Config.saveConfig(Main.this.config, "config.yml");
				System.out.println("Config ファイルを保存しました。");
			}
		});
	}

}

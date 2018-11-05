package net.simplyrin.discord.event.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.User;
import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;
import net.simplyrin.discord.event.exceptions.DiscordException;
import net.simplyrin.threadpool.ThreadPool;

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
public class ModManager {

	private File file;
	private Configuration config;

	public ModManager() {
		this.file = new File("mod.yml");
		if(!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Configuration config = Config.getConfig(this.file);
			config.set("List", Arrays.asList("000000000000000000"));
			Config.saveConfig(config, this.file);
		}

		this.config = Config.getConfig(this.file);

		ThreadPool.run(() -> {
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			ModManager.this.saveAndReload();
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ModManager.this.saveAndReload();
				System.out.println("Moderator 管理ファイルを保存しました。");
			}
		});
	}

	private void saveAndReload() {
		Config.saveConfig(this.config, this.file);
		this.config = Config.getConfig(this.file);
	}

	public void add(User user) throws DiscordException {
		this.add(user.getId());
	}

	public void add(String userId) throws DiscordException {
		List<String> list = this.config.getStringList("List");
		if(list.contains(userId)) {
			throw new DiscordException("このユーザーは既に Moderator に追加されています！");
		}
		list.add(userId);
		this.config.set("List", list);
	}

	public void remove(User user) throws DiscordException {
		this.remove(user.getId());
	}

	public void remove(String userId) throws DiscordException {
		List<String> list = this.config.getStringList("List");
		if(!list.contains(userId)) {
			throw new DiscordException("このユーザーは Moderator ではありません！");
		}
		list.remove(userId);
		this.config.set("List", list);
	}

	public List<String> getModeratorList() {
		return this.config.getStringList("List");
	}

}

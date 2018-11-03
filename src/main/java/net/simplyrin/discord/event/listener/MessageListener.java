package net.simplyrin.discord.event.listener;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.simplyrin.discord.event.Main;
import net.simplyrin.discord.event.exceptions.DiscordException;

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
public class MessageListener extends ListenerAdapter {

	private Main instance;

	public MessageListener(Main instance) {
		this.instance = instance;
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member member = event.getMember();
		User user = event.getUser();
		Guild guild = event.getGuild();

		MessageChannel messageChannel = guild.getTextChannelById(this.instance.getConfig().getString("Channels.Welcome"));

		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.GREEN);
		embedBuilder.setTitle(this.instance.getJda().getSelfUser().getName());
		embedBuilder.setDescription("`" + user.getName() + "` さんがサーバーに参加しました！\n\n大会への参加申請は <#"
				+ this.instance.getConfig().get("Channels.Application") + "> チャンネルに \"参加申請します\" と送信してください。");

		messageChannel.sendMessage(embedBuilder.build()).complete();

		guild.getController().addRolesToMember(member, guild.getRolesByName(this.instance.getConfig().getString("Welcome.Role"), false)).complete();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		User author = event.getAuthor();
		Guild guild = event.getGuild();
		MessageChannel sender = event.getTextChannel();
		String message = event.getMessage().getContentRaw();
		String[] args = message.split(" ");

		if (args.length > 0) {

			try {
				if (sender.getType().equals(ChannelType.PRIVATE)) {
					return;
				}
			} catch (Exception e) {
				return;
			}

			if (sender.getId().equals(this.instance.getConfig().get("Channels.Application"))) {
				if (message.contains("参加") && message.contains("申請")) {

					EmbedBuilder embedBuilder = new EmbedBuilder();
					embedBuilder.setColor(Color.GREEN);
					embedBuilder.setTitle(this.instance.getConfig().getString("TournamentName"));

					try {
						this.instance.getUserManager().add(author.getId());
					} catch (DiscordException e) {
						embedBuilder.setColor(Color.RED);
						embedBuilder.setDescription(author.getAsMention() + " > あなたは参加申請は既にを受け付けています！");
						sender.sendMessage(embedBuilder.build()).complete();
						return;
					}

					embedBuilder.setDescription(author.getAsMention() + " > 大会参加申請を受け付けました！");
					sender.sendMessage(embedBuilder.build()).complete();
				}
				return;
			}

			if (this.instance.getModManager().getModeratorList().contains(author.getId())) {
				if (args[0].equalsIgnoreCase("りすと") || args[0].equalsIgnoreCase("リスト")) {
					List<String> list = this.instance.getUserManager().getUserList();

					EmbedBuilder embedBuilder = new EmbedBuilder();
					embedBuilder.setColor(Color.GREEN);

					int integer = 1;
					for (String userId : list) {
						Member member = guild.getMemberById(userId);
						User user = member.getUser();

						embedBuilder.addField("User #" + integer, user.getName() + " | " + user.getId(), false);
						integer++;
					}

					sender.sendMessage(embedBuilder.build()).complete();
					return;
				}

				if (args[0].equalsIgnoreCase("とーなめんと名") || args[0].equalsIgnoreCase("トーナメント名")) {
					if (args.length > 1) {
						String tournamentName = "";
						for (int i = 1; i < args.length; i++) {
							tournamentName = tournamentName + args[i] + " ";
						}
						tournamentName = tournamentName.substring(0, tournamentName.length() - 1);
						this.instance.getConfig().set("TournamentName", tournamentName);

						sender.sendMessage("トーナメント名を更新しました。\n\n新しいトーナメント名: **" + this.instance.getConfig().getString("TournamentName") + "**").complete();
						return;
					}
					sender.sendMessage("使用方法: トーナメント名 <トーナメント名>").complete();
					return;
				}

				if (args[0].equalsIgnoreCase("シャッフル") || args[0].equalsIgnoreCase("しゃっふる")) {
					List<String> list = this.instance.getUserManager().getUserList();

					if (list.size() == 0) {
						sender.sendMessage("現在参加申請しているユーザーはいません。").complete();
						return;
					}

					String raw = "";

					Collections.shuffle(list);
					for (String userId : list) {
						Member member = guild.getMemberById(userId);

						String nickname = member.getNickname();
						if (nickname != null) {
							raw += nickname + "\n";
						} else {
							raw += member.getUser().getName() + "\n";
						}
					}
					sender.sendMessage("ユーザーリスト:\n\n" + raw).complete();
					return;
				}

			}

			if (args[0].equalsIgnoreCase("t!admin")) {

				if (!this.instance.getModManager().getModeratorList().contains(author.getId())) {
					sender.sendMessage("あなたはこのコマンドを実行する権利がありません。").complete();
					return;
				}

				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("shutdown") && author.getId().equals(this.instance.getConfig().get("Owner"))) {
						sender.sendMessage("Bot をシャットダウンしています...。").complete();
						System.exit(0);
						return;
					}

					if (args[1].equalsIgnoreCase("channel")) {
						if (args.length > 2) {
							MessageChannel messageChannel = guild.getTextChannelById(args[2]);
							if (messageChannel == null) {
								sender.sendMessage("このテキストチャンネルは存在していません！").complete();
								return;
							}
							this.instance.getConfig().set("Channels.Application", args[2]);
						}
						MessageChannel messageChannel = guild.getTextChannelById(this.instance.getConfig().getString("Channels.Application"));
						sender.sendMessage("使用方法: /t!admin channel <channelId>\n\n現在設定されているテキストチャンネル: <#" + messageChannel.getId() + ">").complete();
						return;
					}

					if (args[1].equalsIgnoreCase("tournament")) {
						if (args.length > 2) {
							String tournamentName = "";
							for (int i = 2; i < args.length; i++) {
								tournamentName = tournamentName + args[i] + " ";
							}
							tournamentName = tournamentName.substring(0, tournamentName.length() - 1);
							this.instance.getConfig().set("TournamentName", tournamentName);

							sender.sendMessage("トーナメント名を更新しました。\n\n新しいトーナメント名: **" + this.instance.getConfig().getString("TournamentName") + "**").complete();
							return;
						}

						sender.sendMessage("使用方法: t!admin tournament <トーナメント名>\n\n現在のトーナメント名: **" + this.instance.getConfig().getString("TournamentName") + "**").complete();
						return;
					}

					if (args[1].equalsIgnoreCase("users")) {
						if (args.length > 2) {
							if (args[2].equalsIgnoreCase("add")) {
								if (args.length > 3) {
									Member member = null;
									try {
										member = guild.getMemberById(args[2]);
									} catch (Exception e) {
										sender.sendMessage("このユーザーはサーバーに参加していません！").complete();
										return;
									}
									try {
										this.instance.getUserManager().add(member.getUser().getId());
									} catch (DiscordException e) {
										sender.sendMessage("このユーザーは既に申請リストに追加されています！").complete();
										return;
									}
									sender.sendMessage("ユーザーを申請リストに追加しました。\n\nユーザー名: " + member.getUser().getName() + " | " + member.getUser().getId()).complete();
									return;
								}
								sender.sendMessage("使用方法: t!admin admin users add <userId>").complete();
								return;
							}

							if (args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("kick")) {
								if (args.length > 3) {
									Member member = null;
									try {
										member = guild.getMemberById(args[3]);
									} catch (Exception e) {
										sender.sendMessage("このユーザーはサーバーに参加していません！").complete();
										return;
									}
									try {
										this.instance.getUserManager().remove(member.getUser().getId());
									} catch (DiscordException e) {
										sender.sendMessage("このユーザーは申請リストに追加されていません！").complete();
										return;
									}
									sender.sendMessage("ユーザーを申請リストから削除しました。\n\nユーザー名: " + member.getUser().getName() + " | " + member.getUser().getId()).complete();
									return;
								}
								sender.sendMessage("使用方法: t!admin admin users remove <userId>").complete();
								return;
							}

							if (args[2].equalsIgnoreCase("list")) {
								List<String> list = this.instance.getUserManager().getUserList();

								if (list.size() == 0) {
									sender.sendMessage("現在参加申請しているユーザーはいません。").complete();
									return;
								}

								if (args.length > 3) {
									if (args[3].equalsIgnoreCase("s") || args[3].equalsIgnoreCase("shuffle")) {
										String raw = "";

										Collections.shuffle(list);
										for (String userId : list) {
											Member member = guild.getMemberById(userId);

											String nickname = member.getNickname();
											if (nickname != null) {
												raw += nickname + "\n";
											} else {
												raw += member.getUser().getName() + "\n";
											}
										}
										sender.sendMessage("ユーザーリスト:```" + raw + "```").complete();
										return;
									}
								}

								EmbedBuilder embedBuilder = new EmbedBuilder();
								embedBuilder.setColor(Color.GREEN);

								int integer = 1;
								for (String userId : list) {
									Member member = guild.getMemberById(userId);
									User user = member.getUser();

									embedBuilder.addField("User #" + integer, user.getName() + " | " + user.getId(), false);
									integer++;
								}

								sender.sendMessage(embedBuilder.build()).complete();
								return;
							}

							if (args[2].equalsIgnoreCase("reset")) {
								if (args.length > 3) {
									if (args[3].equalsIgnoreCase("-y")) {
										this.instance.getUserManager().reset();
										sender.sendMessage("申請リストをリセットしました！").complete();
										return;
									}
								}

								sender.sendMessage("参加申請リストをリセットする場合は `t!admin users reset -y` と入力してください。").complete();
								return;
							}
						}
						sender.sendMessage("使用方法: t!admin users <add|remove|list(-shuffle)|reset>").complete();
						return;
					}
				}
				sender.sendMessage("使用方法: t!admin <shutdown|channel|tournament|users> <args>").complete();
				return;
			}

			if (args[0].equalsIgnoreCase("t!mod")) {

				if (!this.instance.getModManager().getModeratorList().contains(author.getId())) {
					sender.sendMessage("あなたはこのコマンドを実行する権利がありません。").complete();
					return;
				}

				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("add")) {
						if (args.length > 2) {
							Member member = null;
							try {
								member = guild.getMemberById(args[3]);
							} catch (Exception e) {
								sender.sendMessage("このユーザーはサーバーに参加していません！").complete();
								return;
							}
							try {
								this.instance.getModManager().add(member.getUser());
							} catch (DiscordException e) {
								sender.sendMessage(e.getMessage()).complete();
								return;
							}
							guild.getController().addRolesToMember(member, guild.getRolesByName(this.instance.getConfig().getString("Roles.Moderator"), false)).complete();
							sender.sendMessage(member.getUser().getName() + " を Moderator にしました！").complete();
							return;
						}
						sender.sendMessage("使用方法: t!mod add <userId>").complete();
						return;
					}

					if (args[1].equalsIgnoreCase("remove")) {
						if (args.length > 2) {
							Member member = null;
							try {
								member = guild.getMemberById(args[2]);
							} catch (Exception e) {
								sender.sendMessage("このユーザーはサーバーに参加していません！").complete();
								return;
							}
							try {
								this.instance.getModManager().remove(member.getUser());
							} catch (DiscordException e) {
								sender.sendMessage(e.getMessage()).complete();
								return;
							}
							guild.getController().removeRolesFromMember(member, guild.getRolesByName(this.instance.getConfig().getString("Roles.Moderator"), false)).complete();
							sender.sendMessage(member.getUser().getName() + " を Moderator から外しました！").complete();
							return;
						}
						sender.sendMessage("使用方法: t!mod remove <userId>").complete();
						return;
					}

					if (args[1].equalsIgnoreCase("list")) {
						List<String> list = this.instance.getModManager().getModeratorList();

						EmbedBuilder embedBuilder = new EmbedBuilder();
						embedBuilder.setColor(Color.GREEN);

						int integer = 1;
						for (String userId : list) {
							Member member = guild.getMemberById(userId);
							User user = member.getUser();

							embedBuilder.addField("User #" + integer, user.getName() + " | " + user.getId(), false);
							integer++;
						}

						sender.sendMessage(embedBuilder.build()).complete();
						return;
					}

				}

				sender.sendMessage("使用方法: t!mod <add|remove|list> <userId>").complete();
				return;
			}
		}
	}

}

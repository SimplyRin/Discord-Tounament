# Discord-Tounament
コードがざつ。

ダウンロードはページの一番下か Release ページからできます。

# Requirement
- Java 8+
- Discord Server
- Discord Bot Token

# Commands
```
トーナメント名を設定する
- t!admin tournament <トーナメント名>
参加申請しているユーザーを表示する
- t!admin users list
参加申請しているユーザーをシャッフルして表示する
- t!admin users list shuffle
参加申請リストに手動でユーザーを追加する
- t!admin users add <ユーザーID>
参加申請リストからユーザーを削除/キックする
- t!admin users remove <ユーザーID>
参加申請リストをリセットする
- t!admin users reset

モデレーターを追加
- t!mod add <ユーザーID>
モデレーターを削除
- t!mod remove <ユーザーID>
モデレーターリストを確認
- t!mod list
```

# Config
```Yaml
Token: BOT_TOKEN_HERE # Discord Bot Token を貼り付け
Owner: '224428706209202177' # Bot 管理者が使用するアカウントID
TournamentName: 'Tounament Name #1' # トーナメント名
Welcome:
  Role: User # サーバーにログインしたときに自動的に割り当てる権利
Channels:
  Welcome: '000000000000000000' # サーバー参加時に誰が参加したかを貼るチャンネル
  Application: '000000000000000000' # "参加申請" を検索するチャンネル(メッセージ受信時)
Roles:
  Moderator: Moderator # モデレーターの権利名
```

# Screenshot
![1](https://i.gyazo.com/fdaafe2a0ca6227326c06a022a7101be.png "1")
![2](https://i.gyazo.com/386bd1e5e60e2ad9da5506355d5e2e10.png "2")

# Download
v1.0: [Discord-Tounament-1.0.jar](https://github.com/SimplyRin/Discord-Tounament/releases/download/1.0/Discord-Tounament-1.0.jar)

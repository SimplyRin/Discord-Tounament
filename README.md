# Discord-Tounament
コードがざつ。

集計をとったリストを表示、シャッフル、管理ユーザー側でユーザーリストを追加、削除などの機能が使えます

ダウンロードはページの一番下か [Release](https://github.com/SimplyRin/Discord-Tounament/releases) ページからできます。

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
**・config.yml**
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

**・mod.yml**
```Yaml
List:
- '000000000000000000'
```

# Screenshot
![1](https://i.gyazo.com/fdaafe2a0ca6227326c06a022a7101be.png "1")
![2](https://i.gyazo.com/386bd1e5e60e2ad9da5506355d5e2e10.png "2")

# Open Source License
**・JDA | Apache License 2.0**
```
  Copyright 2015-2018 Austin Keener & Michael Ritter & Florian Spieß

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ```

**・BungeeCord (Config API) | BSD 3-Clause "New" or "Revised" License**
```
Copyright (c) 2012, md_5. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

The name of the author may not be used to endorse or promote products derived
from this software without specific prior written permission.

You may not use the software for commercial software hosting services without
written permission from the author.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
```


# Download
v1.0: [Discord-Tounament-1.0.jar](https://github.com/SimplyRin/Discord-Tounament/releases/download/1.0/Discord-Tounament-1.0.jar)

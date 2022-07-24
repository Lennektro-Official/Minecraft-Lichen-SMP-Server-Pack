# Too Social
### Status Command
- Use ```/status set <status> <color>``` to set your status.
- The status can be any custom string, but cannot contain any spaces.
- Valid color values are suggested ingame.
- Use ```/status reset``` to have no status.
### NColor
- Admins can use ```/ncolor <player> <color>``` to customize the color of a player.
- To clear a color of a player use ```/ncolor <player> clear```.
### MOTDs
- If you write custom motds into the **motds.txt** every time the server info is requested a random motd out of that file will be send. 
- You can use ```&``` for formatting codes.
### Chat
- You can write formatting codes in chat with ```&```.
- You can customize the player join and quit messages in the **config.yml**.
- Begin a chat message with a players name to write a direct message. If you want to write a players name at the begining of a chat message start the message with a ```!```.

Example | Function
--- | ---
```Len3489KH Welcome to the server!``` | This will send the message ```Welcome to the server!``` as a DM to Len3489KH
```!Len3489KH Welcome to the server!``` | This will send a public message with the content ```Len3489KH Welcome to the server!```

# FEDS Discord Bot

A Discord bot for FEDS FRC Team 201 that listens for student questions and triggers GitHub Actions workflows to generate AI-powered responses.

## How It Works

1. Students mention the bot in Discord with a question
2. Bot acknowledges with ⏳ reaction
3. Bot triggers a GitHub Actions workflow with the question
4. Workflow processes the question and posts the answer back to Discord

## Setup

### Prerequisites

- Node.js 18+ installed on your server
- Discord bot token ([create one here](https://discord.com/developers/applications))
- GitHub Personal Access Token with `workflow` scope ([create one here](https://github.com/settings/tokens))

### Installation

1. Clone the feds-central repository to your Ubuntu server:
   ```bash
   cd /root
   git clone https://github.com/feds201/feds-central.git
   ```

2. Install dependencies:
   ```bash
   cd /root/feds-central/discord/feds-bot
   npm install
   ```

3. Create `.env` file from the example:
   ```bash
   cp .env.example .env
   nano .env  # Edit with your tokens
   ```

4. Test the bot:
   ```bash
   npm start
   ```
   You should see: `✅ Discord bot logged in as YourBot#1234`

## Systemd Setup (Ubuntu/Linode)

1. Copy service file to systemd:
   ```bash
   cd /root/feds-central/discord/feds-bot
   sudo cp feds-bot.service /etc/systemd/system/
   ```

2. Reload systemd and enable the service:
   ```bash
   sudo systemctl daemon-reload
   sudo systemctl enable feds-bot
   sudo systemctl start feds-bot
   ```

3. Check status:
   ```bash
   sudo systemctl status feds-bot
   ```

4. View logs:
   ```bash
   sudo journalctl -u feds-bot -f
   ```

## Management Commands

```bash
# Start the bot
sudo systemctl start feds-bot

# Stop the bot
sudo systemctl stop feds-bot

# Restart the bot (after code updates)
sudo systemctl restart feds-bot

# View status
sudo systemctl status feds-bot

# View logs (live)
sudo journalctl -u feds-bot -f

# View last 100 lines of logs
sudo journalctl -u feds-bot -n 100
```

## Updating the Bot

1. Pull latest code from the repository:
   ```bash
   cd /root/feds-central
   git pull
   ```

2. Install any new dependencies (if package.json changed):
   ```bash
   cd /root/feds-central/discord/feds-bot
   npm install
   ```

3. Restart the service:
   ```bash
   sudo systemctl restart feds-bot
   ```

## Troubleshooting

### Bot won't start
- Check environment variables: `cat .env`
- Check service status: `sudo systemctl status feds-bot`
- View error logs: `sudo journalctl -u feds-bot -n 50`

### Bot can't read messages
Make sure your Discord bot has the following intents enabled in the Discord Developer Portal:
- Message Content Intent
- Server Members Intent

### Workflow not triggering
- Verify GITHUB_TOKEN has `workflow` scope
- Check GitHub repo name and owner are correct
- Verify workflow file exists at `.github/workflows/feds-bot-discord.yml`

## Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `DISCORD_BOT_TOKEN` | Discord bot token from Developer Portal | Yes |
| `GITHUB_TOKEN` | GitHub PAT with workflow scope | Yes |
| `GITHUB_OWNER` | GitHub username or org name | Yes |
| `GITHUB_REPO` | Repository name | Yes |
| `WORKFLOW_FILE` | Workflow filename (default: feds-bot-discord.yml) | No |

## Security Notes

- Never commit `.env` file to git (it's in .gitignore)
- Keep your Discord bot token and GitHub token private
- Use restrictive file permissions on `.env`: `chmod 600 .env`

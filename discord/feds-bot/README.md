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

1. **Install Node.js** (if not already installed):
   ```bash
   apk add nodejs npm
   ```

2. Clone the feds-central repository:
   ```bash
   cd /root
   git clone https://github.com/feds201/feds-central.git
   ```

3. Install dependencies:
   ```bash
   cd /root/feds-central/discord/feds-bot
   npm install
   ```

4. Create `.env` file from the example:
   ```bash
   cp .env.example .env
   nano .env  # Edit with your tokens (install nano: apk add nano)
   ```

5. Test the bot:
   ```bash
   npm start
   ```
   You should see: `✅ Discord bot logged in as YourBot#1234`

## Service Setup

1. Copy service file to OpenRC:
   ```bash
   cd /root/feds-central/discord/feds-bot
   cp feds-bot.openrc /etc/init.d/feds-bot
   chmod +x /etc/init.d/feds-bot
   ```

2. Start and enable the service:
   ```bash
   rc-service feds-bot start
   rc-update add feds-bot default
   ```

3. Check status:
   ```bash
   rc-service feds-bot status
   ```

4. View logs:
   ```bash
   tail -f /var/log/messages
   ```

## Management Commands

```bash
# Start the bot
rc-service feds-bot start

# Stop the bot
rc-service feds-bot stop

# Restart the bot (after code updates)
rc-service feds-bot restart

# View status
rc-service feds-bot status

# View logs
tail -f /var/log/messages
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
   rc-service feds-bot restart
   ```

## Troubleshooting

### Bot won't start
- Check environment variables: `cat .env`
- Check service status: `rc-service feds-bot status`
- View error logs: `tail -n 50 /var/log/messages`

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

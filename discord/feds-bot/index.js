import { Client, GatewayIntentBits } from 'discord.js';
import dotenv from 'dotenv';

dotenv.config();

const GITHUB_OWNER = process.env.GITHUB_OWNER;
const GITHUB_REPO = process.env.GITHUB_REPO;
const WORKFLOW_FILE = process.env.WORKFLOW_FILE || 'feds-bot-discord.yml';
const DISCORD_BOT_TOKEN = process.env.DISCORD_BOT_TOKEN;
const GITHUB_TOKEN = process.env.GITHUB_TOKEN;

// Validate required environment variables
if (!DISCORD_BOT_TOKEN) {
  console.error('❌ DISCORD_BOT_TOKEN is required');
  process.exit(1);
}

if (!GITHUB_TOKEN) {
  console.error('❌ GITHUB_TOKEN is required');
  process.exit(1);
}

if (!GITHUB_OWNER || !GITHUB_REPO) {
  console.error('❌ GITHUB_OWNER and GITHUB_REPO are required');
  process.exit(1);
}

console.log(`🔧 Configuration loaded:`);
console.log(`   GitHub: ${GITHUB_OWNER}/${GITHUB_REPO}`);
console.log(`   Workflow: ${WORKFLOW_FILE}`);

const client = new Client({
  intents: [
    GatewayIntentBits.Guilds,
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.MessageContent,
  ],
});

client.on('ready', () => {
  console.log(`✅ Discord bot logged in as ${client.user.tag}`);
  console.log(`   Servers: ${client.guilds.cache.size}`);
  console.log(`   Ready to receive messages!`);
});

client.on('messageCreate', async (message) => {
  // Ignore bot messages
  if (message.author.bot) return;

  // Only respond to direct @mentions of the bot
  if (!message.mentions.users.has(client.user.id)) return;

  // Strip the mention from the message to get the question
  const question = message.content.replace(/<@!?\d+>/g, '').trim();

  if (!question) {
    await message.reply('Please include a question after mentioning me!');
    return;
  }

  console.log(`📨 Received question from ${message.author.tag}: "${question}"`);

  // Acknowledge receipt
  await message.react('⏳');

  try {
    const response = await fetch(
      `https://api.github.com/repos/${GITHUB_OWNER}/${GITHUB_REPO}/actions/workflows/${WORKFLOW_FILE}/dispatches`,
      {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${GITHUB_TOKEN}`,
          Accept: 'application/vnd.github+json',
          'X-GitHub-Api-Version': '2022-11-28',
        },
        body: JSON.stringify({
          ref: 'main',
          inputs: {
            question,
            channel_id: message.channelId,
            message_id: message.id,
          },
        }),
      }
    );

    if (!response.ok) {
      const body = await response.text();
      console.error(`❌ GitHub API error: ${response.status} ${body}`);
      // Remove hourglass before posting error reply
      await message.reactions.cache.get('⏳')?.users.remove(client.user.id);
      await message.reply('❌ Failed to trigger the workflow. Check server logs.');
    } else {
      console.log(`✅ Workflow triggered successfully for question: "${question}"`);
    }
    // On success, leave the ⏳ - it will be removed when the workflow posts the actual reply
  } catch (err) {
    console.error('❌ Error dispatching workflow:', err);
    // Remove hourglass before posting error reply
    await message.reactions.cache.get('⏳')?.users.remove(client.user.id);
    await message.reply('❌ Something went wrong triggering the workflow.');
  }
});

client.on('error', (error) => {
  console.error('❌ Discord client error:', error);
});

client.on('warn', (info) => {
  console.warn('⚠️  Discord client warning:', info);
});

// Graceful shutdown
const shutdown = async () => {
  console.log('\n🛑 Shutting down gracefully...');
  await client.destroy();
  console.log('✅ Discord bot disconnected');
  process.exit(0);
};

process.on('SIGINT', shutdown);
process.on('SIGTERM', shutdown);

// Login
console.log('🚀 Starting FEDS Discord Bot...');
client.login(DISCORD_BOT_TOKEN).catch((err) => {
  console.error('❌ Failed to login to Discord:', err);
  process.exit(1);
});

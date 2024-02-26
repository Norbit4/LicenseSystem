const { EmbedBuilder } = require("discord.js");

const MessageType ={
    SUCCESS: {color: '#1aa2de', title: 'Success'},
    ERROR: {color: '#EC3912', title: 'Error'},
    WARN: {color: '#E7C81C', title: 'Warn'}
}

/**
 * Create new embed
 * @param {string} message
 * @param {MessageType} messageType
 */
const createEmbed = (message, messageType) =>{
    const {color, title} = messageType;

    return new EmbedBuilder()
        .setTitle(title)
        .setDescription(message)
        .setColor(color);
}

module.exports = { createEmbed, MessageType }
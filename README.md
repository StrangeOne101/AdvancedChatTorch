# AdvancedChatTorch
Source code for a fork of AdvancedChatTorch.

You can find the original here on Spigot: https://www.spigotmc.org/resources/advancedchattorch-advanced-json-chat-formatting-free.38246/

# Changes from the main plugin
- Added support for RGB hex codes in the format of &#RRGGBB
- All characters are now unescaped. Which means you can use \u2714 for a checkmark or \n for a newline
- Fixed PAPI placeholders not being corrected with colors and characters
- Fixed NPE when the perm isn't specified
- Fixed hover texts not supporting 1.16 hex color codes properly
- (Technical change) Moved the plugin to use maven to build

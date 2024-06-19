require conf/distro/poky.conf

DISTRO_NAME = "chronos"
DISTRO_VERSION = "1.0.0"

# use packaging format ipk
PACKAGE_CLASSES = "package_ipk"

# drop native language support
USE_NLS = "no"

# make log dir volatile
VOLATILE_LOG_DIR = "yes"

# users
USERADDEXTENSION = "useradd-staticids"
USERADD_UID_TABLES += "files/static-passwd"
USERADD_GID_TABLES += "files/static-group"

# icecream distributed compile support
INHERIT_DISTRO:append = " icecc"
ICECC_DISABLED ??= "1"

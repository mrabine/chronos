FILESEXTRAPATHS:prepend := "${THISDIR}/linux-rolex:"

require recipes-kernel/linux/linux-intel.inc
require linux-chronos.inc

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids errors when trying
# to build multiple virtual/kernel providers, e.g. as dependency of
# core-image-rt-sdk, core-image-rt.
python () {
    if d.getVar("KERNEL_PACKAGE_NAME") == "kernel" and d.getVar("PREFERRED_PROVIDER_virtual/kernel") != "linux-rolex-rt":
        raise bb.parse.SkipPackage("Set PREFERRED_PROVIDER_virtual/kernel to linux-rolex-rt to enable it")
}

SRC_URI:prepend = " \
    git://github.com/intel/linux-intel-lts.git;branch=${KBRANCH};name=machine;protocol=https \
"

KBRANCH = "6.6/preempt-rt"
KMETA_BRANCH = "yocto-6.6"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION_EXTENSION = "-intel-pk-${LINUX_KERNEL_TYPE}"

LINUX_VERSION = "6.6.23"
SRCREV_machine ?= "73113409624a0a68494db7b1c1d535d1c8b96689"
SRCREV_meta ?= "eb283ea577df80542d48f0c498365960b4c4ecd9"

LINUX_KERNEL_TYPE = "preempt-rt"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc features/security/security.scc"

UPSTREAM_CHECK_GITTAGREGEX = "^lts-(?P<pver>v6.6.(\d+)-rt(\d)-preempt-rt-(\d+)T(\d+)Z)$"

COMPATIBLE_MACHINE = "^(daytona)$"

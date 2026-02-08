SUMMARY = "Chronos init"
DESCRIPTION = "Chronos init base files."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS:${PN} += "busybox-mdev"

SRC_URI += " \
    file://modules \
    file://sysctl.conf \
    file://pci.sh \
    file://system/mountfs \
    file://system/pci \
    file://system/sysctl \
    file://system/depmod \
    file://system/hostname \
    file://system/hwclock \
    file://system/cmdline \
    file://system/urandom \
    file://system/modules \
    file://system/module/any \
    file://system/essential \
    file://net/lo \
"

do_install:append() {
    install -d ${D}${sysconfdir}
    ln -snf ../run/resolv.conf ${D}${sysconfdir}/resolv.conf
    install -m 0644 ${WORKDIR}/modules ${D}${sysconfdir}/modules
    install -m 755 -d ${D}${sysconfdir}/sysctl.d
    install -m 644 ${WORKDIR}/sysctl.conf ${D}${sysconfdir}/sysctl.conf
    ln -snf ../sysctl.conf ${D}${sysconfdir}/sysctl.d/99-sysctl.conf
    install -d ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/pci.sh ${D}${sysconfdir}/mdev/pci.sh

    install -d ${D}${initng_system_dir}
    install -m 0755 ${WORKDIR}/system/mountfs ${D}${initng_system_dir}/mountfs
    install -m 0755 ${WORKDIR}/system/pci ${D}${initng_system_dir}/pci
    install -m 0755 ${WORKDIR}/system/sysctl ${D}${initng_system_dir}/sysctl
    install -m 0755 ${WORKDIR}/system/depmod ${D}${initng_system_dir}/depmod
    install -m 0755 ${WORKDIR}/system/hostname ${D}${initng_system_dir}/hostname
    install -m 0755 ${WORKDIR}/system/hwclock ${D}${initng_system_dir}/hwclock
    install -m 0755 ${WORKDIR}/system/cmdline ${D}${initng_system_dir}/cmdline
    install -m 0755 ${WORKDIR}/system/urandom ${D}${initng_system_dir}/urandom
    install -m 0755 ${WORKDIR}/system/modules ${D}${initng_system_dir}/modules
    install -m 0755 ${WORKDIR}/system/essential ${D}${initng_system_dir}/essential

    install -d ${D}${initng_system_dir}/module
    install -m 0755 ${WORKDIR}/system/module/any ${D}${initng_system_dir}/module/any

    install -d ${D}${initng_net_dir}
    install -m 0755 ${WORKDIR}/net/lo ${D}${initng_net_dir}/lo
}

FILES:${PN} += " \
    ${sysconfdir}/resolv.conf \
    ${sysconfdir}/modules \
    ${sysconfdir}/sysctl.conf \
    ${sysconfdir}/mdev/* \
    ${sysconfdir}/sysctl.d/* \
    ${initng_system_dir}/module/* \
"

pkg_postinst:${PN}:append() {
    if [ -f "$D${sysconfdir}/mdev.conf" ]; then
        echo "" >> "$D${sysconfdir}/mdev.conf"
        echo "eth[0-9].* 0:0 660 @${sysconfdir}/mdev/pci.sh" >> "$D${sysconfdir}/mdev.conf"
    fi
}

inherit initng

INITNG_TARGETS:${PN} = " \
    system/mountfs \
    system/pci \
    system/sysctl \
    system/depmod \
    system/hostname \
    system/hwclock \
    system/cmdline \
    system/urandom \
    system/modules \
    system/essential \
    net/lo \
"

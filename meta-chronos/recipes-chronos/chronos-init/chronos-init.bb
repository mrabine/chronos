SUMMARY = "Chronos init"
DESCRIPTION = "Chronos init base files."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += " \
    file://sysctl.conf \
"

do_install:append() {
    install -m 755 -d ${D}${sysconfdir}/sysctl.d
    install -m 644 ${WORKDIR}/sysctl.conf ${D}${sysconfdir}/sysctl.conf
    ln -snf ../sysctl.conf ${D}${sysconfdir}/sysctl.d/99-sysctl.conf
    ln -snf ../run/resolv.conf ${D}${sysconfdir}/resolv.conf
}

inherit initng

INITNG_TARGETS:${PN} = " \
    system/mountfs \
    system/sysctl \
    system/hostname \
    system/essentials \
    net/lo \
"

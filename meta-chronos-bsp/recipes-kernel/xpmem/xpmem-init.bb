SUMMARY = "Autoload XPMEM Kernel Module"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS:${PN} += "kernel-module-xpmem"

inherit allarch

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 /dev/null ${D}${sysconfdir}/modules-load.d/xpmem.conf
    echo "xpmem" >> ${D}${sysconfdir}/modules-load.d/xpmem.conf
}

FILES:${PN} += "${sysconfdir}/modules-load.d/xpmem.conf"

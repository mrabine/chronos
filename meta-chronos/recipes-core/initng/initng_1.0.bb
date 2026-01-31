SUMMARY = "Next Generation Init System (initng)"
DESCRIPTION = "Initng is a full replacement of the old and in many ways deprecated sysvinit tool. \
               It is designed with speed in mind, doing as much as possible asynchronously. \
               In other words: \
               It will boot your unix-system much faster, and give you more control and statistics over your system."
HOMEPAGE = "http://initng.org/"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "pkgconfig-native acr-native jam-native"
RDEPENDS:${PN} = "base-files"

SRC_URI = "git://github.com/initng/initng.git;branch=master;protocol=https"
SRCREV = "a54d7757490202be4074a05bf8338de2965b5219"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

SRC_URI += " \
    file://disable-unused-modules.patch \
    file://fix-broken-build.patch \
    file://fix-ngcclient.patch \
    file://fix-runiscrit.patch \
    file://fix-runlevel.patch \
    file://sanitize-stdio.patch \
    file://fix-fmon.patch \
    file://default \
"

PACKAGECONFIG ?= ""
PACKAGECONFIG[init] = "--enable-install-as-init,--disable-install-as-init,,"
PACKAGECONFIG[colors] = "--enable-colors,--disable-colors,,"

do_configure() {
    cd ${S}
    acr
    AR="${AR} cr" \
    CFLAGS="${CFLAGS}" \
    CC="${CC} ${LDFLAGS}" \
    ./configure \
    --build="${BUILD_SYS}" \
    --host="${HOST_SYS}" \
    --target="${TARGET_SYS}" \
    --prefix="" \
    --libdir="${base_libdir}" \
    --sbindir="${base_sbindir}" \
    --sysconfdir="${sysconfdir}" \
    ${PACKAGECONFIG_CONFARGS}
}

do_compile() {
    cd ${S}
    AR="${AR} cr" \
    CFLAGS="${CFLAGS}" \
    CC="${CC} ${LDFLAGS}" \
    DESTDIR="${B}" \
    jam
}

do_install() {
    cd ${S}
    AR="${AR} cr" \
    CFLAGS="${CFLAGS}" \
    CC="${CC} ${LDFLAGS}" \
    DESTDIR="${D}" \
    jam install
    chmod 0755 ${D}${base_libdir}/*.so*
    install -d -m 0755 ${D}/${sysconfdir}/initng
    install -d -m 0770 -g initng ${D}/${sysconfdir}/initng/runlevel
    install -m 0750 -g initng ${WORKDIR}/default ${D}/${sysconfdir}/initng/runlevel
    ln -snf preinit ${D}${base_sbindir}/init
}

FILES:${PN} += " \
    ${base_libdir}/initng/*.so \
    ${base_libdir}/initng/ibin/* \
    ${base_libdir}/initng/wrappers/default \
"

FILES:${PN}-dev += " \
    /include/* \
    ${base_libdir}/pkgconfig/* \
    ${base_libdir}/initng/wrappers/sh \
    ${base_libdir}/initng/wrappers/gentoo \
"

FILES:${PN}-doc += " \
    /share/* \
"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "${PN}"
GROUPADD_PARAM:${PN} = "${PN}"

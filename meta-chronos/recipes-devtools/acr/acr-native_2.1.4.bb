SUMMARY = "Replacement for autoconf"
DESCRIPTION = "ACR tries to replace autoconf functionality generating a full-compatible \
               configure script (runtime flags). But using shell-script instead of m4. \
               This means that ACR is faster, smaller and easy to use."
HOMEPAGE = "https://github.com/radareorg/acr"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "git://github.com/radareorg/acr.git;branch=master;protocol=https"
SRCREV = "ac9f0d400a6152d04fa30cffea2c79c29ab156cd"

S = "${WORKDIR}/git"

inherit autotools native

do_install() {
    install -d -m 0755 ${D}${bindir}
    sed -e 's,@''VERSION@,${PV},g' "${S}/src/acr" > "${D}${bindir}/acr"
    chmod +x "${D}${bindir}/acr"
    sed -e 's,@''VERSION@,${PV},g' "${S}/src/acr-cat" > "${D}${bindir}/acr-cat"
    chmod +x "${D}${bindir}/acr-cat"
    sed -e 's,@''VERSION@,${PV},g' "${S}/src/acr-sh" > "${D}${bindir}/acr-sh"
    chmod +x "${D}${bindir}/acr-sh"
    install -m 0755 "${S}/src/amr" "${D}${bindir}/amr"
    install -m 0755 "${S}/src/acr-wrap" "${D}${bindir}/acr-wrap"
    install -m 0755 "${S}/src/acr-install" "${D}${bindir}/acr-install"
}

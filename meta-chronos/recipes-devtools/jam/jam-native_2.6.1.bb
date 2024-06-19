SUMMARY = "Just Another Make (jam)"
DESCRIPTION = "A make replacement that makes building simple things simple and building complicated things manageable."
HOMEPAGE = "https://swarm.workshop.perforce.com/projects/perforce_software-jam"

LICENSE = "PD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/PD;md5=b3597d12946881e13cb3b548d1173851"

SRC_URI = "https://swarm.workshop.perforce.com/downloads/guest/perforce_software/jam/jam-2.6.1.tar"
SRC_URI[sha256sum] = "ace6b227c1a9985934fd13c9c0ae4f7ff44171c6ded8b83bb3da75e6efdcb3a7"

inherit native

EXTRA_OEMAKE += " 'CC=${CC}' LOCATE_TARGET=./bin.${HOST_SYS}"
AR += "ru"

do_compile() {
    oe_runmake
}

do_install() {
    install -d -m 0755 ${D}${bindir}
    install -m 0755 ${S}/bin.${HOST_SYS}/jam ${D}${bindir}
}

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

hostname:pn-base-files = "chronos"

do_install:append(){
    echo 'LANG="en_US.UTF-8"' > ${D}${sysconfdir}/locale.conf
}

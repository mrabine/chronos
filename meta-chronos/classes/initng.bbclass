INITNG_PACKAGES ?= "${PN}"
INITNG_AUTO_ENABLE ??= "enable"

python __anonymous() {
    if bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d):
        d.appendVar('DEPENDS', ' initng')
        if not bb.utils.contains('DISTRO_FEATURES', 'sysvinit', True, False, d):
            d.setVar("INHIBIT_UPDATERCD_BBCLASS", "1")
}

initng_postinst() {
    if [ ! -d $D${sysconfdir}/initng ]; then
        mkdir -p $D${sysconfdir}/initng
    fi
    if [ ! -e $D${sysconfdir}/initng/default.runlevel ]; then
        touch $D${sysconfdir}/initng/default.runlevel
        chmod 760 $D${sysconfdir}/initng/default.runlevel
        chown root:initng $D${sysconfdir}/initng/default.runlevel
    fi
    if [ "${INITNG_AUTO_ENABLE}" = "enable" ]; then
        for target in ${INITNG_TARGETS_ESCAPED}; do
            if ! grep -e "${target}" -f $D${sysconfdir}/initng/default.runlevel; then
                echo "${target}" >> $D${sysconfdir}/initng/default.runlevel
            fi
        done
    fi
}

initng_populate_packages[vardeps] += "initng_postinst"

python initng_populate_packages() {
    import shlex

    if not bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d):
        return

    def initng_append_rdep(pkg):
        d.appendVar('RDEPENDS:%s' % pkg, ' initng')

    def initng_append_file(pkg):
        initng_dir = os.path.join(d.getVar('sysconfdir'), 'initng')
        for target in d.getVar('INITNG_TARGETS:%s' % pkg).split():
            d.appendVar('FILES:%s' % pkg, ' ' + os.path.join(initng_dir, target))

    def initng_generate_package_scripts(pkg):
        paths_escaped = ' '.join(shlex.quote(s) for s in d.getVar('INITNG_TARGETS:%s' % pkg).split())
        d.setVar('INITNG_TARGETS_ESCAPED:%s' % pkg, paths_escaped)
        localdata = d.createCopy()
        localdata.prependVar("OVERRIDES", pkg + ":")
        postinst = d.getVar('pkg_postinst:%s' % pkg)
        if not postinst:
            postinst = '#!/bin/sh\n'
        postinst += localdata.getVar('initng_postinst')
        d.setVar('pkg_postinst:%s' % pkg, postinst)

    if os.path.exists(d.getVar("D")):
        for pkg in d.getVar('INITNG_PACKAGES').split():
            if d.getVar('INITNG_TARGETS:%s' % pkg):
                initng_append_rdep(pkg)
                initng_append_file(pkg)
                initng_generate_package_scripts(pkg)
}

PACKAGESPLITFUNCS =+ "initng_populate_packages"

def get_initng_targets(d):
    services = []
    pkgs = d.getVar('INITNG_PACKAGES').strip()
    for pkg in pkgs.split():
        targets = (d.getVar('INITNG_TARGETS:%s' % pkg) or "").strip()
        for target in targets.split():
            services.append(target)
    return " ".join(services)

def get_initng_src_uri(d):
    if not bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d):
        return ''
    sources = []
    for target in get_initng_targets(d).split():
        sources.append("file://" + target)
    return " ".join(sources)

SRC_URI += "${@get_initng_src_uri(d)}"

do_install:append:class-target() {
    if ${@bb.utils.contains('DISTRO_FEATURES','initng','true','false',d)}; then
        targets="${@get_initng_targets(d)}"
        for target in $targets; do
            install -D -m 0755 ${WORKDIR}/${target} ${D}${sysconfdir}/initng/${target}
        done
    fi
}

python rm_sysvinit_initdir() {
    import shutil
    if bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d) and \
        not bb.utils.contains('DISTRO_FEATURES', 'sysvinit', True, False, d):
        sysvinit_initdir = oe.path.join(d.getVar("D"), (d.getVar('INIT_D_DIR') or "/etc/init.d"))
        if os.path.exists(sysvinit_initdir):
            shutil.rmtree(sysvinit_initdir)
}

python rm_systemd_initdir() {
    import shutil
    if bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d) and \
        not bb.utils.contains('DISTRO_FEATURES', 'systemd', True, False, d):
        systemd_initdir = oe.path.join(d.getVar("D"), d.getVar('systemd_unitdir'))
        if os.path.exists(systemd_initdir):
            shutil.rmtree(systemd_initdir)
        systemd_libdir = os.path.dirname(systemd_initdir)
        if (os.path.exists(systemd_libdir) and not os.listdir(systemd_libdir)):
            os.rmdir(systemd_libdir)
}

python rm_initng_initdir() {
    import shutil
    if not bb.utils.contains('DISTRO_FEATURES', 'initng', True, False, d):
        initng_initdir = oe.path.join(d.getVar("D"), "/etc/initng")
        if os.path.exists(initng_initdir):
            shutil.rmtree(initng_initdir)
}

do_install[postfuncs] += "${RM_INIT_DIR} "
RM_INIT_DIR:class-target = " rm_sysvinit_initdir rm_systemd_initdir rm_initng_initdir "
RM_INIT_DIR = ""

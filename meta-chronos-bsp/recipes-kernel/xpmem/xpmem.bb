SUMMARY = "XPMEM: Cross-Partition Memory"
DESCRIPTION = "A Linux kernel module that allows a process to map the memory of another process into its own address space."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS += "autoconf-native automake-native libtool-native"

inherit module

SRC_URI = "git://github.com/hpc/xpmem;branch=master;protocol=https"
SRCREV = "3bcab55479489fdd93847fa04c58ab16e9c0b3fd"

S = "${WORKDIR}/git/kernel"

EXTRA_OECONF = " \
    --enable-kernel-module \
    --with-kerneldir=${STAGING_KERNEL_DIR} \
"

do_configure:prepend() {
    cd ${WORKDIR}/git
    if [ ! -f configure ]; then
        ./autogen.sh
    fi
    ./configure \
        --build=${BUILD_SYS} \
        --host=${HOST_SYS} \
        --target=${TARGET_SYS} \
        --prefix=${prefix} \
        ac_cv_member_struct_task_struct_cpus_mask=yes \
        ${EXTRA_OECONF}
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/xpmem
    install -m 0644 ${S}/xpmem.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/xpmem/
}

INSANE_SKIP:${PN}-src += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"

RPROVIDES:${PN} += "kernel-module-${PN}"

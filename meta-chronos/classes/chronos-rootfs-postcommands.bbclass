ROOTFS_POSTPROCESS_COMMAND += '${@bb.utils.contains("IMAGE_FEATURES", ["empty-root-password", "serial-autologin-root"], "initng_serial_autologin_root; ", "", d)}'

initng_serial_autologin_root(){
    if ${@bb.utils.contains("DISTRO_FEATURES", "initng", "true", "false", d)}; then
        if [ -e ${IMAGE_ROOTFS}${sysconfdir}/initng/daemon/getty ]; then
            sed -i 's/exec getty/exec getty --autologin root/' \
                "${IMAGE_ROOTFS}${sysconfdir}/initng/daemon/getty"
        fi
    fi
}

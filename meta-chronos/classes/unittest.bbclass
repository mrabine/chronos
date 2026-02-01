PACKAGE_BEFORE_PN += "${PN}-test"

DEPENDS += "gtest gmock"
RDEPENDS:${PN}-test += "${PN}"

RRECOMMENDS:${PN}-test += "kernel-module-dummy"

PACKAGECONFIG += "test"

FILES:${PN}-test += "${prefix}/share/${PN}/test/*"

PKG_UNITTEST_DIR = "${WORKDIR}/unittest"
IMG_UNITTEST_DIR = "${TMPDIR}/unittest/${MACHINE}"

do_populate_unit_test[dirs] = "${IMG_UNITTEST_DIR} ${B}"
do_populate_unit_test[stamp-extra-info] = "${MACHINE}"

addtask do_populate_unit_test after do_install before do_package

python do_populate_unit_test(){
    import glob, os

    pn = d.getVar('PN')
    dstdir = d.getVar('D')
    prefix = d.getVar('prefix')
    outdir = d.getVar('PKG_UNITTEST_DIR')

    filename = os.path.join(outdir, pn)
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    tests = os.path.join(dstdir, prefix.lstrip('/'), 'share', pn, 'test', '*')

    with open(filename, 'w') as f:
        for test in glob.glob(tests):
            test = test.replace(dstdir, '', 1)
            f.write(test + '\n')
}

SSTATETASKS += "do_populate_unit_test"

do_populate_unit_test[sstate-inputdirs] = "${PKG_UNITTEST_DIR}"
do_populate_unit_test[sstate-outputdirs] = "${IMG_UNITTEST_DIR}"

# ensure destination exists
do_populate_unit_test_setscene[dirs] = "${IMG_UNITTEST_DIR}"

addtask do_populate_unit_test_setscene

python do_populate_unit_test_setscene(){
    sstate_setscene(d)
}

# do_populate_unit_test can't be executed multiple times because it will induce a write in a shared dir.
# do_populate_test is an empty intermediate step added to avoid multiple execution of do_populate_unit_test.
addtask do_populate_test after do_populate_unit_test

do_populate_test(){
}

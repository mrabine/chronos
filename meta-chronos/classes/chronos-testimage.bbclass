inherit testimage

# package index generation is forced in image recipe (see. chronos-image:do_generate_package_index)
# in order to have package feed index generated on every image build, no need to have this done twice
TESTIMAGEDEPENDS:remove = "package-index:do_package_index"

# disable kvm
QEMU_USE_KVM = ""

# add our custom variables to TESTIMAGE_UPDATE_VARS
TESTIMAGE_UPDATE_VARS += "TEST_CASES_SKIP"

# ensure test manifests are generated
do_testimage_dry[recrdeptask] += "do_populate_test"

# add dependency between do_testimage and dummy do_testimage_dry
addtask do_testimage_dry after do_image_qa before do_testimage

# do nothing, only pull in dependencies
python do_testimage_dry() {
}

# always rerun testimage
do_testimage[nostamp] = "1"

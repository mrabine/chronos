IMAGE_CLASSES += "qemuboot"

QB_SYSTEM_NAME:daytona = "qemu-system-x86_64"
QB_CPU:daytona = "-cpu max"
QB_NETWORK_DEVICE:daytona = "-device pci-bridge,chassis_nr=1,id=pci.1"
QB_NETWORK_DEVICE:daytona:append = " -device virtio-net-pci,netdev=net0,bus=pci.1,addr=0x01,mac=@MAC@"
QB_CMDLINE_IP_TAP:daytona = "ip=192.168.7.@CLIENT@::192.168.7.@GATEWAY@:255.255.255.0::enp1s1:off:8.8.8.8"

QB_SYSTEM_NAME:tank = "qemu-system-aarch64"
QB_MACHINE:tank = "-machine virt"
QB_CPU:tank = "-cpu max"
QB_NETWORK_DEVICE:tank = "-device pci-bridge,chassis_nr=1,id=pci.1"
QB_NETWORK_DEVICE:tank:append = " -device virtio-net-pci,netdev=net0,bus=pci.1,addr=0x01,mac=@MAC@"
QB_CMDLINE_IP_TAP:tank = "ip=192.168.7.@CLIENT@::192.168.7.@GATEWAY@:255.255.255.0::enp1s1:off:8.8.8.8"

QB_MEM = "-m 2048"
QB_OPT_APPEND = "-rtc base=utc,clock=host"

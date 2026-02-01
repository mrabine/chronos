# chronos

A Yocto-based Linux distribution featuring a PREEMPT-RT patched kernel for real-time applications.

## Overview

Chronos is a custom Linux distribution built with the Yocto Project, designed for real-time performance with a PREEMPT-RT kernel patch. It supports multiple hardware platforms and provides a minimal, optimized system for embedded and real-time applications.

## Table of contents

* [Overview](#overview)
* [Prerequisites](#prerequisites)
* [Repository Setup](#repository-setup)
* [Build Environment](#build-environment)
* [Building](#building)
* [Running](#running)
* [Testing](#testing)
* [Debugging](#debugging)
* [Additional Information](#additional-information)
* [Resources](#resources)
* [License](#license)

## Prerequisites

### System Requirements

- Ubuntu 20.04 LTS or newer (recommended)
- At least 50GB of free disk space
- Minimum 8GB RAM (16GB recommended)
- Multi-core processor for faster builds

### Installing Dependencies

Update your system packages:

```bash
sudo apt update && sudo apt upgrade -y
```

Install required build tools:

```bash
sudo apt install build-essential chrpath cpio diffstat file gawk lz4 tmux zstd iptables python3-setuptools python3-venv
```

Create a Python virtual environment:

```bash
python3 -m venv venv
```

## Repository Setup

Clone the repository with all submodules:

```bash
git clone --recurse-submodules https://github.com/mrabine/chronos.git
```

If you've already cloned without `--recurse-submodules`, initialize them with:

```bash
git submodule update --init --recursive
```

Move to chronos directory:

```bash
cd chronos
```

## Build Environment

Initialize the Yocto build environment:

```bash
source ./meta-chronos-distro/chronos-init-build-env
```

> [!NOTE]
> This script must be sourced (using `.` or `source`) in each new terminal session before building.

## Building

### Supported Machines

| Machine | Architecture | Description |
|---------|-------------|-------------|
| `daytona` | x86_64 | Default configuration for x86-64 systems |
| `tank` | ARM64 | ARM 64-bit architecture support |

### Building Image

Build the image for your target machine:

```bash
MACHINE=daytona bitbake chronos-image
```

Or for ARM64:

```bash
MACHINE=tank bitbake chronos-image
```

> [!TIP]
> **First-time builds** (without local or remote cache) can take several hours depending on your system. Subsequent builds will be significantly faster due to local caching.

### Build Artifacts

After a successful build, you'll find the output images in:

```
build/tmp/deploy/images/<machine>/
```

## Running

### QEMU Emulation

Test your image using QEMU:

```bash
MACHINE=daytona runqemu chronos-image nographic
```

> [!TIP]
> **Keyboard shortcuts** to exit QEMU: `Ctrl+A` then `X`

### Deploying to Hardware

Flash the image to your target device using tools like:
- **dd** for SD cards
- **bmaptool** for efficient flashing
- Your platform's specific flashing tool

Example using dd:

```bash
sudo dd if=build/tmp/deploy/images/daytona/chronos-image-daytona.wic \
        of=/dev/sdX bs=4M status=progress && sync
```

> [!WARNING]
> Replace `/dev/sdX` with your actual device. Double-check to avoid data loss!

## Testing

### Automated Image Testing

Chronos supports automated testing using Yocto's testimage framework. This runs a suite of tests on the image in QEMU (or real hardware) to verify functionality.

#### Prerequisites

Customize which tests to run on your image by including the following in `conf/local.conf`:

```bash
TEST_SUITES = "ping ssh parselogs"
```

#### Running Tests

Build the image and run tests in QEMU:

```bash
MACHINE=daytona bitbake chronos-image -c testimage
```

Tests will automatically:
1. Boot the image in QEMU (or real hardware)
2. Execute the configured test suites
3. Report results to the console and log files

#### Test Results

Test results are saved to:

```
build/tmp/log/oeqa/testresults.json
```

Detailed logs are available in:

```
build/tmp/log/oeqa/
```

#### Available Test Suites

Common included test suites:

| Test Suite | Description |
|-----------|-------------|
| `ping` | Basic network connectivity test |
| `ssh` | SSH server functionality |
| `parselogs` | System log error checking |
| `python` | Python interpreter tests |
| `gcc` | GCC compiler tests |
| `kernelmodule` | Kernel module loading tests |

#### Custom Tests

You can create custom test cases by adding Python test modules to your layer:

```
meta-chronos/lib/oeqa/runtime/cases/mytest.py
```

## Debugging

### Setting Up Development Tools

Generate IDE support files:

```bash
MACHINE=daytona bitbake meta-ide-support
```

Build native development sysroot:

```bash
MACHINE=daytona bitbake -c build_native_sysroot build-sysroots
```

Build target sysroot for cross-compilation:

```bash
MACHINE=daytona bitbake -c build_target_sysroot build-sysroots
```

### Using the SDK

Extract and use the SDK for application development:

```bash
MACHINE=daytona bitbake chronos-image -c populate_sdk
```

The SDK installer will be located in `build/tmp/deploy/sdk/`.

## Additional Information

### Useful Commands

**Build a specific recipe:**

```bash
bitbake <recipe-name>
```

**Execute a specific task of a specific recipe (fetch, unpack, configure, compile etc...):**

```bash
bitbake -c <task-name> <recipe-name>
```

**Clean a specific recipe:**

```bash
bitbake -c clean <recipe-name>
```

**Clean all in order to rebuild from scratch:**

```bash
bitbake -c cleanall <recipe-name>
```

**Show recipe dependencies:**

```bash
bitbake -g chronos-image
```

## Resources

### Official Documentation

- [Yocto Project Documentation](https://docs.yoctoproject.org/) - Complete documentation hub
- [Yocto Project Overview and Concepts Manual](https://docs.yoctoproject.org/overview-manual/index.html) - Understanding Yocto concepts
- [Yocto Project Development Tasks Manual](https://docs.yoctoproject.org/dev-manual/index.html) - Common development tasks
- [Yocto Project Test Environment Manual](https://docs.yoctoproject.org/test-manual/index.html) - Testimage framework documentation
- [BitBake User Manual](https://docs.yoctoproject.org/bitbake/index.html) - BitBake build tool reference

### Layer Resources

- [OpenEmbedded Layer Index](https://layers.openembedded.org/) - Search and browse available layers
- [Yocto Project Compatible Layers](https://www.yoctoproject.org/software-overview/layers/) - Compatible layer listing

## License

* **License:** Licensed under the [MIT](LICENSE) License.

# chronos

## Table of contents

* [Prerequisites](#prerequisites-id)
* [Repository cloning](#clone-id)
* [Build environment setup](#setup-id)
* [Building the image](#build-id)
* [Running the image](#run-id)
* [Debugging the image](#debug-id)

## Prerequisites <a id="prerequisites-id"></a>

```bash
sudo apt install build-essential chrpath cpio diffstat file gawk lz4 tmux zstd python3-setuptools
```

## Repository cloning <a id="clone-id"></a>

```bash
git clone https://github.com/mrabine/chronos.git
```

```bash
cd chronos
```

```bash
git submodule update --init --recursive
```

## Build environment setup <a id="setup-id"></a>

```bash
. ./meta-chronos-distro/chronos-init-build-env
```

## Building the image <a id="build-id"></a>

> [!NOTE]
> Available machines: **daytona** (default)

```bash
MACHINE=<machine> bitbake chronos-image
```

## Running the image <a id="run-id"></a>

```bash
runqemu nographic
```

## Debugging the image <a id="debug-id"></a>

```bash
MACHINE=<machine> bitbake meta-ide-support
```

```bash
MACHINE=<machine> bitbake -c build_native_sysroot build-sysroots
```

```bash
MACHINE=<machine> bitbake -c build_target_sysroot build-sysroots
```

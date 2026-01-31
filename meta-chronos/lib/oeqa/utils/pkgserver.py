#!/usr/bin/env python3
"""
Module running a server to serve packages.
"""

import os
from oeqa.utils.httpserver import HTTPService

class PkgServer():
    """
    Package server that serves packages via HTTP to remote targets.
    """

    def __init__(self, tc, machine, arch, tmpdir):
        """
        Initialize the package server.

        :tc: test context
        :machine: machine type (e.g. 'daytona' etc...).
        :arch: architecture name (e.g. 'corei7-64', 'cortexa57' etc...).
        :tmpdir: temporary directory containing the package repository.
        """
        self.tc = tc
        self.machine = machine
        self.arch = arch
        self.tmpdir = tmpdir
        self.http_server = None

    def _update_package_lists(self):
        status, output = self.tc.target.run("opkg update")
        if status != 0:
            raise RuntimeError(f"failed to update package lists:\n{output}")

    def _create_package_config(self):
        """
        Inject package server configuration dynamically on the target.
        """
        if not self.http_server:
            raise RuntimeError("HTTP server not running")

        add_list = [self.arch, self.machine]
        serv_url = f"http://{self.http_server.host}:{self.http_server.port}/ipk"
        content = "\n".join(f"src/gz {entry} {serv_url}/{entry}" for entry in add_list)
        self.tc.target.run(f"echo '{content}' > /etc/opkg/base-feeds.conf")

        self._update_package_lists()

    def start_server(self, server_ip, port=8080):
        """
        Start the HTTP server to serve packages.

        :server_ip: server ip address.
        :port: server port.
        """
        if self.http_server:
            return

        pkg_dir = os.path.join(self.tmpdir, "deploy")
        self.http_server = HTTPService(pkg_dir, server_ip, port)

        try:
            self.http_server.start()
        except Exception as error:
            raise Exception(f"{error}\n{server_ip}:{port}")

        self._create_package_config()

    def stop_server(self):
        """
        Stop the HTTP server.
        """
        if self.http_server:
            self.http_server.stop()
            self.http_server = None

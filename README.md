# SimpleWAR

An example Java WAR application used to showcase deployments in the Ansible TPs.

## Build

If you have nix setup, you can simply run `nix build` to build the WARs answering with version 1.1.2, 1.1.3 and 1.1.4., which you will find in the `result` directory. Nix is cool !

Otherwise, you must ensure you have maven and a JDK21 installed and run `maven -Dversion=1.1.x clean verify` once for each version.

## Endpoints

### /

The IndexServlet returns a sentence about the environment it runs in. Default is DEV, and will
use whatever is in the 'env_name' key of an 'app.properties' file found by the class loader.

### /version

This endpoint returns an "application/json" page containing the version maven property as the value to the "version" key.



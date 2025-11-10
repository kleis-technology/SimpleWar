{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/master";
    flake-utils.url = "github:numtide/flake-utils";
  };
  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
    }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = nixpkgs.legacyPackages."${system}";
        buildSimpleWar =
          {
            lib,
            maven,
            version,
          }:
          maven.buildMavenPackage {
            pname = "simplewar";
            inherit version;
            src = lib.sources.cleanSource ./.;
            mvnHash = "sha256-V7MHDppzHBCWsc6y2YG3GRxG5KGCWht5p7UzVcwWd+o=";
            mvnParameters = "-Dversion=${version}";

            installPhase = ''
              mkdir -p "$out"
              cp target/SimpleWar.war "$out/SimpleWar-${version}.war"
            '';
          };
      in
      {
        packages = {
          default = pkgs.symlinkJoin {
            name = "simplewar";
            paths = map (pkgs.callPackage buildSimpleWar) (
              map (v: { version = v; }) [
                "1.1.2"
                "1.1.3"
                "1.1.4"
              ]
            );
          };
        };
        devShells.default = pkgs.mkShell {
          packages = with pkgs; [
            jdk21_headless
            jdt-language-server
            maven
          ];
        };
      }
    );
}

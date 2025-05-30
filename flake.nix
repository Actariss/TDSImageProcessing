{
  description = "Netbeans flake. Kinda works until it doesn't.";

  inputs.nixpkgs.url = "nixpkgs/nixpkgs-unstable";

  outputs = inputs: let
    system = "x86_64-linux";
    pkgs = import inputs.nixpkgs {inherit system;};
  in {
    devShells.${system}.default = pkgs.mkShell {
      packages = with pkgs; [jdk netbeans wmname];
      shellHook = ''
        wmname LG3D
        export JAVA_HOME=${pkgs.jdk}
        export PATH="${pkgs.jdk}/bin:$PATH"

        java -version
      '';
    };
  };
}

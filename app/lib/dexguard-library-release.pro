# DexGuard configuration for release versions.
# Copyright (c) 2012-2016 GuardSquare NV
#
# Note that the DexGuard plugin jars generally contain their own copies
# of this file.

# Keep all public API.
-keep public class * {
    public protected *;
}

# Keep all asset files, with their original names.
-keepresourcefiles assets/**

-include dexguard-library-release-aggressive.pro

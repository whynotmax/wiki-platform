module.exports = {
    webpack: {
        configure: (webpackConfig) => {
            // Remove CaseSensitivePathsPlugin to avoid case/path issues
            webpackConfig.plugins = webpackConfig.plugins.filter(
                (plugin) =>
                    plugin.constructor.name !== "CaseSensitivePathsPlugin"
            );
            return webpackConfig;
        },
    },
};
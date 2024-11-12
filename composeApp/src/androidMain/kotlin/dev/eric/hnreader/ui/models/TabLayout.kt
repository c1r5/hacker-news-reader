package dev.eric.hnreader.ui.models

sealed class TabLayout(
    val tabItem: TabItem,
) {
    data object Trends : TabLayout(
        tabItem = TabItem(
            title = "Trends"
        )
    )

    data object News : TabLayout(
        tabItem = TabItem(
            title = "News"
        )
    )

    data object Jobs : TabLayout(
        tabItem = TabItem(
            title = "Jobs"
        )
    )
}
# Expandable RecyclerView

#### ExpandableItemView in ScrollView:
![materialdots](https://github.com/emircanpekyurek/ExpandableRecyclerView/blob/master/readme_images/single.gif)
#### ExpandableRecyclerView
![materialdots](https://github.com/emircanpekyurek/ExpandableRecyclerView/blob/master/readme_images/recycler.gif)

## Installation
(app) build.gradle:
```gradle
implementation 'com.github.emircanpekyurek:ExpandableRecyclerView:1.0.4'
```

#### AND

(root) build.gradle:
```gradle
allprojects {
    repositories {
        ..
        maven { url "https://jitpack.io" }
    }
}
```
or settings.gradle:
```gradle
dependencyResolutionManagement {
    ..
    repositories {
        ..
        maven { url 'https://jitpack.io' }
    }
}
```

## Usage

for collapsed attributes:
![materialdots](https://github.com/emircanpekyurek/ExpandableRecyclerView/blob/master/readme_images/collapsed_usage.png)

expanded attributes:
![materialdots](https://github.com/emircanpekyurek/ExpandableRecyclerView/blob/master/readme_images/expanded_usage.png)

for horizontal:
![materialdots](https://github.com/emircanpekyurek/ExpandableRecyclerView/blob/master/readme_images/horizontal_usage.png)

## Usage For ExpandableRecyclerView

Create a layout xml file with only ExpandableItemView in it. eg:**custom_expandable_recycler_view**

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.emircan.expandablerecyclerview.ExpandableItemView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:animation_duration="200"
    app:collapsed_icon_tint="#787878"
    app:expanded_icon_tint="#000000"
    app:line_color="#D6D6D6"
    app:recycler_layout_manager="GridLayoutManager"
    app:recycler_padding="5dp"
    app:recycler_span_count="3"
    app:show_view_line="true"
    app:title="Custom Grid View"
    app:title_color="#000000" />
 />
```

add ExpandableRecyclerView to activity or fragment:
```xml
<com.emircan.expandablerecyclerview.ExpandableRecyclerView
    android:id="@+id/expandableRecyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
use setData function:

```kotlin
val adapter = ... //RecyclerView adapter
val list = listOf<ExpandableItem>(ExpandableItem("Item 1", adapter))
val singleExpandItem = true //for single expandable
expandableRecyclerView.setData(R.layout.custom_expandable_recycler_view, list, singleExpandItem)
```



#### Custom Attributes:
| Attribute | Description |
| --- | --- |
| `expanded_icon` | expanded icon  |
| `expanded_icon_tint` | expanded icon color |
| `collapsed_icon` | collapsed icon |
| `collapsed_icon_tint` | collapsed icon color |
| `show_view_line` | show line when collapsed |
| `line_color` | line color |
| `title` | title text |
| `title_color` | title color |
| `android:textSize` | title text size |
| `animation_duration` | animation time of expand and collapse |
| `recycler_padding` | expanded recycler padding |
| `android:orientation` | horizontal or vertical for LinearLayoutManager |
| `recycler_span_count` | spanCount for Grid |
| `recycler_layout_manager` | LinearLayoutManager, GridLayoutManager or StaggeredGridLayoutManager |

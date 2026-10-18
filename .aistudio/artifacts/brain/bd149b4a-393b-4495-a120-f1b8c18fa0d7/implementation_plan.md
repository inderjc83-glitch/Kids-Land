# Remove Cluttered Side Badge Tags from HomeScreen Cards

Remove all awkward, vertically wrapping side badge tags (`act.badge`) from the activity cards on the Home screen to create a clean, spacious, uncluttered layout where titles and subtitles render smoothly without squishing or vertical line breaks.

## User Review & Critical Decisions

> [!IMPORTANT]
> The user confirmed the following choices via clarification:
> - **Badge Handling**: Completely remove all side badge pills (`Soft Voice`, `Addition up to 10`, `A-Z & 0-10`, `Creative`, `Brain Booster`) from the activity cards on the Home screen.
> - **Screen Margins**: Keep existing standard screen margins as requested.

---

### 1. Overview & Core Concept
- **Problem**: Activity cards placed badge pills (`act.badge`) inside a horizontal `Row` next to titles. On mobile screens, long titles such as *"Alphabet & Numbers Tracing"* squeezed the badge into a narrow width, forcing words to awkwardly wrap letter-by-letter (e.g. `A-Z \n & \n 0-1 \n 0`), making the UI look distorted and unpolished.
- **Solution**: Remove the badge property and badge rendering from `ActivityTile` in `HomeScreen.kt`. The title will occupy the full width above its subtitle in a clean vertical column, restoring a balanced, readable, and child-friendly card appearance.

---

### 2. User Experience & Visual Design
- **Card Layout**:
  ```
  ┌────────────────────────────────────────────────────────┐
  │  [🦊 Icon]   Heading 1: Alphabet & Numbers Tracing   > │
  │              Heading 2: Handwriting practice on pad   │
  └────────────────────────────────────────────────────────┘
  ```
- **Visual Improvements**:
  - No awkwardly wrapping badge chips on the right of headings.
  - Generous horizontal space for titles across all screen sizes and mobile aspect ratios.
  - Clean vertical hierarchy: Heading 1 (Title) on top, Heading 2 (Subtitle) directly beneath.

---

### 3. Technical Changes

```
HomeScreen.kt
 ├── ActivityTile data class: remove `badge: String? = null`
 ├── activities list: remove all badge arguments
 └── Activity Card Layout: remove the side badge `Surface` component next to Heading 1
```

1. **`HomeScreen.kt`**:
   - Update `data class ActivityTile` to remove `val badge: String? = null`.
   - In `activities` list, remove `badge = "..."` from all tiles (`AppScreen.VOCAB_MENU`, `AppScreen.MATH`, `AppScreen.TRACING`, `AppScreen.COLORING`, `AppScreen.MEMORY`, `AppScreen.REWARDS`).
   - In `ActivityCard` layout, remove the `act.badge?.let { ... }` block and simplify the title `Text` directly inside the column.

---

### 4. Verification Plan
- Run `compile_applet` to ensure flawless compilation.
- Verify that cards display large titles, clean subtitles, and no distorted vertical pills.

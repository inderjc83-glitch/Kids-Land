package com.example.data

import com.example.model.AnimalDrawing
import com.example.model.ColorableZone
import com.example.model.RewardBadge
import com.example.model.Sticker
import com.example.model.TracingGuide
import com.example.model.VocabCategory
import com.example.model.VocabItem

object EducationalContentRegistry {

    // --- VOCABULARY: COLORS (English Only, Clean & Polite) ---
    val colorsList = listOf(
        VocabItem("c1", VocabCategory.COLORS, "Red", "Like a sweet apple", "🔴", 0xFFFF5252, "Apples and strawberries are bright red!", "Look at the shiny red apple."),
        VocabItem("c2", VocabCategory.COLORS, "Blue", "Like the sunny sky", "🔵", 0xFF29B6F6, "The ocean and clear sky are beautiful blue!", "The little blue bird sings happily."),
        VocabItem("c3", VocabCategory.COLORS, "Green", "Like fresh grass", "🟢", 0xFF66BB6A, "Leaves, grass, and frogs are lovely green!", "The gentle green frog hops high."),
        VocabItem("c4", VocabCategory.COLORS, "Yellow", "Like warm sunshine", "🟡", 0xFFFFD54F, "The warm sun and sweet bananas are yellow!", "The bright yellow sun gives us warmth."),
        VocabItem("c5", VocabCategory.COLORS, "Orange", "Like a juicy fruit", "🟠", 0xFFFF7043, "Sweet oranges and crunchy carrots are orange!", "Fresh orange juice is healthy and sweet."),
        VocabItem("c6", VocabCategory.COLORS, "Purple", "Like yummy grapes", "🟣", 0xFFAB47BC, "Juicy grapes and shiny plums are purple!", "The purple grapes taste sweet."),
        VocabItem("c7", VocabCategory.COLORS, "Pink", "Like a blossom flower", "🌸", 0xFFF06292, "Sweet lotus flowers and flamingos are pretty pink!", "A soft pink butterfly landed on my hand."),
        VocabItem("c8", VocabCategory.COLORS, "Brown", "Like a cuddly teddy", "🟤", 0xFF8D6E63, "Warm chocolate and cute teddy bears are brown!", "The brown teddy bear gives warm hugs."),
        VocabItem("c9", VocabCategory.COLORS, "Black", "Like the night sky", "⚫", 0xFF37474F, "The starry night sky and tires are black!", "The night sky is peaceful and full of stars."),
        VocabItem("c10", VocabCategory.COLORS, "White", "Like fluffy clouds", "⚪", 0xFFECEFF1, "Fluffy clouds, milk, and soft snow are pure white!", "White clouds float gently in the sky.")
    )

    // --- VOCABULARY: ANIMALS (English Only) ---
    val animalsList = listOf(
        VocabItem("a1", VocabCategory.ANIMALS, "Lion", "Lion - King of the jungle", "🦁", 0xFFFFB300, "The brave lion has a golden mane and rests peacefully.", "The lion rests gently under the acacia tree."),
        VocabItem("a2", VocabCategory.ANIMALS, "Elephant", "Elephant - Gentle giant", "🐘", 0xFF90A4AE, "Elephants have large ears and a long trunk to drink fresh water.", "The kind elephant sprays cool water playfully."),
        VocabItem("a3", VocabCategory.ANIMALS, "Monkey", "Monkey - Playful jumper", "🐒", 0xFFA1887F, "Monkeys love jumping on trees and eating sweet bananas.", "The cheeky monkey swings from vine to vine."),
        VocabItem("a4", VocabCategory.ANIMALS, "Tiger", "Tiger - Striped friend", "🐅", 0xFFFF7043, "Tigers have beautiful orange fur with dark stripes.", "The majestic tiger walks softly in the forest."),
        VocabItem("a5", VocabCategory.ANIMALS, "Giraffe", "Giraffe - Tallest friend", "🦒", 0xFFFFCA28, "Giraffes are the tallest animals in the world.", "The tall giraffe eats tender leaves from tree branches."),
        VocabItem("a6", VocabCategory.ANIMALS, "Dog", "Dog - Loyal pet friend", "🐶", 0xFF8D6E63, "Dogs are friendly, loyal, and love wagging their tails.", "The cheerful dog plays with the bouncing ball."),
        VocabItem("a7", VocabCategory.ANIMALS, "Cat", "Cat - Soft kitty", "🐱", 0xFFBA68C8, "Cats purr softly when they are happy and take cozy naps.", "The fluffy cat purrs while drinking warm milk."),
        VocabItem("a8", VocabCategory.ANIMALS, "Bear", "Bear - Sweet honey lover", "🐻", 0xFF6D4C41, "Bears love eating sweet golden honey in the woods.", "The cuddly brown bear rests by the river."),
        VocabItem("a9", VocabCategory.ANIMALS, "Zebra", "Zebra - Striped runner", "🦓", 0xFF546E7A, "Every zebra has unique black and white stripes.", "The friendly zebra runs across the sunny meadow."),
        VocabItem("a10", VocabCategory.ANIMALS, "Rabbit", "Rabbit - Bouncy bunny", "🐰", 0xFFCFD8DC, "Rabbits have soft long ears and love crunchy carrots.", "The cute rabbit hops gently across the grass."),
        VocabItem("a11", VocabCategory.ANIMALS, "Panda", "Panda - Bamboo eater", "🐼", 0xFF455A64, "Pandas love munching on tender green bamboo stalks.", "The giant panda rolls over peacefully."),
        VocabItem("a12", VocabCategory.ANIMALS, "Kangaroo", "Kangaroo - Big hopper", "🦘", 0xFFBCAAA4, "Mother kangaroos carry their little babies in a cozy front pouch.", "The kangaroo leaps high across the plains.")
    )

    // --- VOCABULARY: BIRDS (English Only) ---
    val birdsList = listOf(
        VocabItem("b1", VocabCategory.BIRDS, "Parrot", "Parrot - Colorful talker", "🦜", 0xFF66BB6A, "Parrots have bright green feathers and repeat friendly words.", "The cheerful parrot loves crunchy seeds."),
        VocabItem("b2", VocabCategory.BIRDS, "Peacock", "Peacock - Royal dancer", "🦚", 0xFF0288D1, "The peacock opens its gorgeous feathers like a colorful fan.", "The dancing peacock celebrates the arrival of rain."),
        VocabItem("b3", VocabCategory.BIRDS, "Eagle", "Eagle - Soaring flier", "🦅", 0xFF8D6E63, "Eagles fly very high and have sharp eyesight.", "The mighty eagle soars above the mountain tops."),
        VocabItem("b4", VocabCategory.BIRDS, "Sparrow", "Sparrow - Morning chirper", "🐦", 0xFFA1887F, "Sparrows are friendly little birds that chirp happily every morning.", "The tiny sparrow chirps by the garden window."),
        VocabItem("b5", VocabCategory.BIRDS, "Owl", "Owl - Wise night bird", "🦉", 0xFF6D4C41, "Owls can see wonderfully in the night and turn their heads around.", "The wise little owl watches the starry night quietly."),
        VocabItem("b6", VocabCategory.BIRDS, "Pigeon", "Pigeon - Gentle cooer", "🕊️", 0xFFB0BEC5, "Pigeons are peaceful birds that coo softly.", "White pigeons flutter gently in the park."),
        VocabItem("b7", VocabCategory.BIRDS, "Duck", "Duck - Pond swimmer", "🦆", 0xFF43A047, "Ducks love swimming in ponds and make funny quack sounds.", "The yellow ducks paddle in the clean water."),
        VocabItem("b8", VocabCategory.BIRDS, "Penguin", "Penguin - Snowy slider", "🐧", 0xFF37474F, "Penguins wear tuxedo coats and slide on icy snow on their bellies.", "The playful penguin dives into the cool ocean."),
        VocabItem("b9", VocabCategory.BIRDS, "Flamingo", "Flamingo - Pink balance", "🦩", 0xFFF06292, "Flamingos stand gracefully on one leg and have soft pink feathers.", "Pink flamingos wade peacefully in the lake."),
        VocabItem("b10", VocabCategory.BIRDS, "Woodpecker", "Woodpecker - Tree builder", "🪵", 0xFFE53935, "Woodpeckers tap gently on tree bark to build cozy nests.", "Tap tap tap! The woodpecker works on the tree.")
    )

    // --- VOCABULARY: MONTHS (English Only) ---
    val monthsList = listOf(
        VocabItem("m1", VocabCategory.MONTHS, "January", "January - Month One", "❄️", 0xFF4FC3F7, "First month of the year. Cool winter breezes and fresh beginnings.", "January has 31 days. Happy New Year!"),
        VocabItem("m2", VocabCategory.MONTHS, "February", "February - Month Two", "💖", 0xFFF06292, "Second month of the year. The shortest month with 28 or 29 days.", "February brings sweet flowers and cozy mornings."),
        VocabItem("m3", VocabCategory.MONTHS, "March", "March - Month Three", "🌱", 0xFF81C784, "Third month. Spring arrives with blooming flowers and green leaves.", "March has 31 days. Plants begin to sprout."),
        VocabItem("m4", VocabCategory.MONTHS, "April", "April - Month Four", "🌦️", 0xFFFFD54F, "Fourth month. Fresh spring showers bring bright blooming gardens.", "April has 30 days. April showers bring May flowers."),
        VocabItem("m5", VocabCategory.MONTHS, "May", "May - Month Five", "☀️", 0xFFFFB74D, "Fifth month. Sunny bright days and sweet fruits arrive.", "May has 31 days. Time for joyful summer games."),
        VocabItem("m6", VocabCategory.MONTHS, "June", "June - Month Six", "🏖️", 0xFFFF8A65, "Sixth month. Long sunny days and refreshing cool treats.", "June has 30 days. Perfect for outdoor fun."),
        VocabItem("m7", VocabCategory.MONTHS, "July", "July - Month Seven", "🌧️", 0xFF4DD0E1, "Seventh month. Summer warmth and refreshing raindrops.", "July has 31 days. Playing in the afternoon breeze."),
        VocabItem("m8", VocabCategory.MONTHS, "August", "August - Month Eight", "🌈", 0xFF81D4FA, "Eighth month. Look up to spot beautiful colorful rainbows.", "August has 31 days. Bright sunny afternoons."),
        VocabItem("m9", VocabCategory.MONTHS, "September", "September - Month Nine", "🍂", 0xFFFFB300, "Ninth month. Gentle autumn breezes as leaves turn golden.", "September has 30 days. Welcoming pleasant weather."),
        VocabItem("m10", VocabCategory.MONTHS, "October", "October - Month Ten", "🎃", 0xFFFF7043, "Tenth month. Crisp autumn days and sweet treats everywhere.", "October has 31 days. Flying kites in the cool breeze."),
        VocabItem("m11", VocabCategory.MONTHS, "November", "November - Month Eleven", "🧣", 0xFFBA68C8, "Eleventh month. Warm sweaters and family gratitude.", "November has 30 days. Wrapping up in warm scarves."),
        VocabItem("m12", VocabCategory.MONTHS, "December", "December - Month Twelve", "🎄", 0xFF66BB6A, "Twelfth month. Sparkling holiday cheer and winter joy.", "December has 31 days. Singing joyful holiday songs.")
    )

    // --- VOCABULARY: WEEKDAYS (English Only) ---
    val daysList = listOf(
        VocabItem("d1", VocabCategory.DAYS, "Monday", "Monday - Day One", "🎒", 0xFF29B6F6, "First day of the school week. Time to learn new exciting things.", "On Monday we start our fresh learning adventure."),
        VocabItem("d2", VocabCategory.DAYS, "Tuesday", "Tuesday - Day Two", "🎨", 0xFFFF9800, "Second day of the week. Great day for coloring and art fun.", "On Tuesday we paint colorful pictures."),
        VocabItem("d3", VocabCategory.DAYS, "Wednesday", "Wednesday - Day Three", "⚽", 0xFF66BB6A, "Middle of the week! Time to run and play soccer.", "On Wednesday we play games in the grassy park."),
        VocabItem("d4", VocabCategory.DAYS, "Thursday", "Thursday - Day Four", "🔬", 0xFFAB47BC, "Fourth day of the week. Perfect for puzzles and curious discoveries.", "On Thursday we explore nature and science."),
        VocabItem("d5", VocabCategory.DAYS, "Friday", "Friday - Day Five", "🎵", 0xFFF06292, "Fifth day of the week. Fun music day with songs and clapping.", "On Friday we sing songs and dance to happy beats."),
        VocabItem("d6", VocabCategory.DAYS, "Saturday", "Saturday - Day Six", "🚲", 0xFFFFD54F, "Weekend begins! Riding bicycles and family outdoor fun.", "On Saturday we ride our bikes in the park."),
        VocabItem("d7", VocabCategory.DAYS, "Sunday", "Sunday - Day Seven", "🍨", 0xFF00E5FF, "Peaceful holiday! Relaxing, reading bedtime rhymes, and smiles.", "Sunday is a calm and happy family day.")
    )

    // --- TRACING GUIDES (A-Z and 0-10) ---
    val alphabetTracingList = listOf(
        TracingGuide("A", false, "Apple", "🍎", "Start at top, draw down left, down right, then across"),
        TracingGuide("B", false, "Bear", "🐻", "Line down, top curve, bottom curve"),
        TracingGuide("C", false, "Cat", "🐱", "Big round curve open on the right"),
        TracingGuide("D", false, "Duck", "🦆", "Straight line down, big round curve to the right"),
        TracingGuide("E", false, "Elephant", "🐘", "Line down, three horizontal arms across"),
        TracingGuide("F", false, "Fish", "🐟", "Line down, two horizontal arms across"),
        TracingGuide("G", false, "Giraffe", "🦒", "Curve around like C, line up and step in"),
        TracingGuide("H", false, "House", "🏡", "Two vertical poles, connected across the middle"),
        TracingGuide("I", false, "Ice Cream", "🍦", "Tall straight pole with a cap and base"),
        TracingGuide("J", false, "Juice", "🧃", "Line down curving into a gentle hook"),
        TracingGuide("K", false, "Kite", "🪁", "Straight line down, kick in and kick out"),
        TracingGuide("L", false, "Lion", "🦁", "Line down, then slide to the right"),
        TracingGuide("M", false, "Monkey", "🐒", "Up, slant down, slant up, and straight down"),
        TracingGuide("N", false, "Nest", "🪺", "Line up, diagonal slide down, line straight up"),
        TracingGuide("O", false, "Owl", "🦉", "Big round loop all the way around"),
        TracingGuide("P", false, "Penguin", "🐧", "Line down, one loop at the top"),
        TracingGuide("Q", false, "Queen", "👑", "Round circle with a small walking tail"),
        TracingGuide("R", false, "Rainbow", "🌈", "Line down, top loop, and kick out leg"),
        TracingGuide("S", false, "Sun", "☀️", "Swerving snake curve left and right"),
        TracingGuide("T", false, "Tiger", "🐅", "Top horizontal roof, line down in the center"),
        TracingGuide("U", false, "Umbrella", "☂️", "Curve down and loop back up like a smile"),
        TracingGuide("V", false, "Van", "🚐", "Slant down, then slant back up to a point"),
        TracingGuide("W", false, "Whale", "🐳", "Two sharp V shapes holding hands"),
        TracingGuide("X", false, "Xylophone", "🎵", "Two diagonal lines crossing in the middle"),
        TracingGuide("Y", false, "Yo-yo", "🪀", "Small V shape meeting a line going down"),
        TracingGuide("Z", false, "Zebra", "🦓", "Line across, diagonal zig-zag down, line across base")
    )

    val numberTracingList = listOf(
        TracingGuide("1", true, "One Sun", "☀️", "Straight line down from top to bottom"),
        TracingGuide("2", true, "Two Eyes", "👀", "Curve around top, slide down, line flat across base"),
        TracingGuide("3", true, "Three Stars", "⭐", "Half round curve, then another half round curve"),
        TracingGuide("4", true, "Four Wheels", "🚗", "Down and turn right, tall line straight down"),
        TracingGuide("5", true, "Five Fingers", "✋", "Line down, round belly curve, top hat across"),
        TracingGuide("6", true, "Six Cherries", "🍒", "Slide down curve and wrap into a round circle loop"),
        TracingGuide("7", true, "Seven Colors", "🌈", "Line across the top, diagonal slide down"),
        TracingGuide("8", true, "Eight Legs", "🐙", "Make an S curve, then loop back up to close"),
        TracingGuide("9", true, "Nine Balloons", "🎈", "Round balloon loop on top, then line down"),
        TracingGuide("10", true, "Ten Stars", "🌟", "Number 1 down, followed by a round zero circle")
    )

    // --- ANIMAL DRAWINGS FOR COLORING ---
    val animalDrawings = listOf(
        AnimalDrawing(
            "draw_puppy", "Playful Puppy", "🐶", "Cozy Doghouse", "Puppies love fetching tennis balls and wagging their tail!",
            listOf(
                ColorableZone("body", "Puppy Body", 0xFFFFFFFF),
                ColorableZone("ears", "Floppy Ears", 0xFFFFFFFF),
                ColorableZone("collar", "Collar", 0xFFFFFFFF),
                ColorableZone("nose", "Nose & Paws", 0xFFFFFFFF)
            )
        ),
        AnimalDrawing(
            "draw_lion", "Brave Little Lion", "🦁", "Warm Savannah", "Lions take naps for up to 20 hours a day!",
            listOf(
                ColorableZone("mane", "Lion Mane", 0xFFFFFFFF),
                ColorableZone("face", "Face", 0xFFFFFFFF),
                ColorableZone("crown", "Royal Crown", 0xFFFFFFFF),
                ColorableZone("paws", "Paws", 0xFFFFFFFF)
            )
        ),
        AnimalDrawing(
            "draw_dolphin", "Happy Dolphin", "🐬", "Blue Lagoon", "Dolphins can jump 20 feet high out of the water!",
            listOf(
                ColorableZone("body", "Dolphin Body", 0xFFFFFFFF),
                ColorableZone("belly", "Smooth Belly", 0xFFFFFFFF),
                ColorableZone("fin", "Fins & Tail", 0xFFFFFFFF),
                ColorableZone("water", "Splash Waves", 0xFFFFFFFF)
            )
        ),
        AnimalDrawing(
            "draw_butterfly", "Magical Butterfly", "🦋", "Flower Garden", "Butterflies taste sweet nectar with their tiny feet!",
            listOf(
                ColorableZone("wing_top", "Upper Wings", 0xFFFFFFFF),
                ColorableZone("wing_bottom", "Lower Wings", 0xFFFFFFFF),
                ColorableZone("body", "Body & Antenna", 0xFFFFFFFF),
                ColorableZone("spots", "Magic Wing Spots", 0xFFFFFFFF)
            )
        ),
        AnimalDrawing(
            "draw_turtle", "Friendly Turtle", "🐢", "Sandy Beach", "Turtles carry their protective house on their backs!",
            listOf(
                ColorableZone("shell", "Shell Patterns", 0xFFFFFFFF),
                ColorableZone("skin", "Head & Legs", 0xFFFFFFFF),
                ColorableZone("belly", "Under Belly", 0xFFFFFFFF),
                ColorableZone("spots", "Shell Hexagons", 0xFFFFFFFF)
            )
        )
    )

    // --- REWARD BADGES ---
    val rewardBadges = listOf(
        RewardBadge("b_math", "Math Wizard", "🧙‍♂️", "Solve 5 math challenges correctly", 20),
        RewardBadge("b_trace", "Master Calligrapher", "✍️", "Trace 10 alphabets smoothly", 35),
        RewardBadge("b_color", "Star Picasso", "🎨", "Complete and color 3 animal artworks", 50),
        RewardBadge("b_vocab", "Word Champion", "🗣️", "Hear pronunciation of 20 vocabulary items", 65),
        RewardBadge("b_streak", "Streak Master", "🔥", "Maintain a 3-day learning streak", 80)
    )

    // --- STICKERS ---
    val stickersList = listOf(
        Sticker("s1", "Golden Star", "⭐", 5, true),
        Sticker("s2", "Shiny Apple", "🍎", 5, true),
        Sticker("s3", "Winner Trophy", "🏆", 10, true),
        Sticker("s4", "Cute Puppy", "🐶", 15, false),
        Sticker("s5", "Space Rocket", "🚀", 20, false),
        Sticker("s6", "Rainbow Cloud", "🌈", 25, false),
        Sticker("s7", "Sweet Cupcake", "🧁", 30, false),
        Sticker("s8", "Cute Kitten", "🐱", 35, false)
    )
}

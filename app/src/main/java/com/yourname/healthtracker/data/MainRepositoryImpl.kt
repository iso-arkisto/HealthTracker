package com.yourname.healthtracker.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.yourname.healthtracker.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(): MainRepository {
    private val foodList = listOf<Food>(
//        Food(1, R.string.water,"drink",0.0,0.0,0.0,0.0),
//        Food(2,R.string.apple,"food",52.0,0.25,13.8,0.15),
//        Food(3, R.string.banana, "food", 89.0,1.1,22.8,0.3),
//        Food(4, "raw potato", "food", 77.0, 2.0, 17.5, 0.1),
//        Food(5, "raw carrot", "food", 41.0, 0.9, 9.6, 0.2),
//        Food(6, "tangerine", "food", 53.0, 0.8, 13.3, 0.3),

        // Вода и базовые напитки
        Food(1, R.string.water_plain, FoodType.DRINK, 0.0, 0.0, 0.0, 0.0),
        Food(2, R.string.water_mineral, FoodType.DRINK, 0.0, 0.0, 0.0, 0.0),
        Food(3, R.string.sparkling_water, FoodType.DRINK, 0.0, 0.0, 0.0, 0.0),
        Food(4, R.string.herbal_tea, FoodType.DRINK, 2.0, 0.0, 0.0, 0.5),
        Food(5, R.string.green_tea, FoodType.DRINK, 1.0, 0.0, 0.0, 0.2),
        Food(6, R.string.black_tea, FoodType.DRINK, 1.0, 0.0, 0.0, 0.3),
        Food(7, R.string.coffee_black, FoodType.DRINK, 2.0, 0.1, 0.0, 0.0),
        Food(8, R.string.coffee_latte, FoodType.DRINK, 62.0, 3.5, 2.5, 5.0),
        Food(9, R.string.cappuccino, FoodType.DRINK, 45.0, 2.8, 1.8, 3.5),
        Food(10, R.string.espresso, FoodType.DRINK, 3.0, 0.2, 0.1, 0.3),
        Food(11, R.string.americano, FoodType.DRINK, 5.0, 0.3, 0.2, 0.5),

        // Соки
        Food(12, R.string.orange_juice, FoodType.DRINK, 45.0, 0.7, 0.2, 10.4),
        Food(13, R.string.apple_juice, FoodType.DRINK, 46.0, 0.1, 0.1, 11.3),
        Food(14, R.string.grape_juice, FoodType.DRINK, 60.0, 0.3, 0.1, 14.8),
        Food(15, R.string.tomato_juice, FoodType.DRINK, 17.0, 0.9, 0.1, 3.5),
        Food(16, R.string.carrot_juice, FoodType.DRINK, 40.0, 0.9, 0.2, 9.3),

        // Молочные напитки
        Food(17, R.string.milk_25, FoodType.DRINK, 52.0, 2.8, 2.5, 4.7),
        Food(18, R.string.milk_32, FoodType.DRINK, 60.0, 2.9, 3.2, 4.7),
        Food(19, R.string.kefir, FoodType.DRINK, 53.0, 2.9, 2.5, 4.0),
        Food(20, R.string.ayran, FoodType.DRINK, 40.0, 2.0, 1.5, 3.5),
        Food(21, R.string.protein_shake, FoodType.DRINK, 120.0, 25.0, 1.5, 4.0),

                // 🥛 Молочные продукты
        Food(34, R.string.yogurt_greek, FoodType.FOOD, 66.0, 10.0, 0.4, 4.0),
    Food(35, R.string.yogurt_natural, FoodType.FOOD, 68.0, 5.0, 3.2, 3.5),
    Food(36, R.string.cottage_cheese_5, FoodType.FOOD, 121.0, 17.2, 5.0, 1.8),
    Food(37, R.string.cottage_cheese_9, FoodType.FOOD, 159.0, 16.7, 9.0, 2.0),
    Food(38, R.string.sour_cream_15, FoodType.FOOD, 162.0, 2.6, 15.0, 3.0),
    Food(39, R.string.cheese_russian, FoodType.FOOD, 363.0, 23.0, 29.0, 0.0),
    Food(40, R.string.cheese_mozzarella, FoodType.FOOD, 280.0, 28.0, 17.0, 0.0),
    Food(41, R.string.cheese_cheddar, FoodType.FOOD, 402.0, 25.0, 33.0, 1.3),
    Food(42, R.string.cheese_feta, FoodType.FOOD, 264.0, 14.0, 21.0, 4.1),
    Food(43, R.string.butter, FoodType.FOOD, 748.0, 0.5, 82.5, 0.8),
    Food(44, R.string.cream_10, FoodType.FOOD, 118.0, 2.5, 10.0, 4.0),
    Food(45, R.string.cream_20, FoodType.FOOD, 205.0, 2.5, 20.0, 3.5),

    // 🍗 Мясо и птица
    Food(46, R.string.chicken_breast, FoodType.FOOD, 113.0, 23.6, 1.9, 0.4),
    Food(47, R.string.chicken_thigh, FoodType.FOOD, 209.0, 16.8, 15.8, 0.0),
    Food(48, R.string.beef_fillet, FoodType.FOOD, 187.0, 19.4, 11.9, 0.0),
    Food(49, R.string.pork_loin, FoodType.FOOD, 242.0, 19.7, 17.9, 0.0),
    Food(50, R.string.turkey_breast, FoodType.FOOD, 135.0, 19.5, 5.5, 0.0),
    Food(51, R.string.duck_breast, FoodType.FOOD, 201.0, 19.7, 13.2, 0.0),
    Food(52, R.string.lamb_leg, FoodType.FOOD, 232.0, 18.0, 17.0, 0.0),
    Food(53, R.string.bacon, FoodType.FOOD, 541.0, 37.0, 42.0, 1.4),
    Food(54, R.string.sausage_boiled, FoodType.FOOD, 260.0, 12.0, 23.0, 2.0),
    Food(55, R.string.sausage_smoked, FoodType.FOOD, 407.0, 15.0, 38.0, 2.5),

    // 🐟 Рыба и морепродукты
    Food(56, R.string.salmon, FoodType.FOOD, 208.0, 20.0, 13.0, 0.0),
    Food(57, R.string.tuna_fresh, FoodType.FOOD, 184.0, 25.0, 9.0, 0.0),
    Food(58, R.string.cod, FoodType.FOOD, 82.0, 18.0, 0.7, 0.0),
    Food(59, R.string.trout, FoodType.FOOD, 148.0, 20.5, 7.2, 0.0),
    Food(60, R.string.mackerel, FoodType.FOOD, 205.0, 18.0, 14.0, 0.0),
    Food(61, R.string.herring, FoodType.FOOD, 158.0, 18.0, 9.0, 0.0),
    Food(62, R.string.shrimp, FoodType.FOOD, 85.0, 18.0, 1.0, 0.0),
    Food(63, R.string.squid, FoodType.FOOD, 92.0, 16.0, 1.4, 3.1),
    Food(64, R.string.mussels, FoodType.FOOD, 86.0, 12.0, 2.2, 3.7),
    Food(65, R.string.canned_tuna, FoodType.FOOD, 116.0, 25.0, 0.8, 0.0),
    Food(66, R.string.canned_sardines, FoodType.FOOD, 208.0, 25.0, 11.0, 0.0),

    // 🥚 Яйца и продукты
//    Food(67, R.string.egg_chicken, FoodType.FOOD, 157.0, 12.7, 11.5, 0.7),
//    Food(68, R.string.egg_quail, FoodType.FOOD, 158.0, 13.1, 11.1, 0.4),
//    Food(69, R.string.egg_white, FoodType.FOOD, 52.0, 10.9, 0.2, 0.7),
//    Food(70, R.string.egg_yolk, FoodType.FOOD, 322.0, 16.2, 26.5, 1.0),
//
//    // 🌾 Крупы и злаки
//    Food(71, R.string.buckwheat, FoodType.FOOD, 101.0, 4.2, 1.1, 21.3),
//    Food(72, R.string.oatmeal, FoodType.FOOD, 88.0, 3.0, 1.7, 15.0),
//    Food(73, R.string.rice_white, FoodType.FOOD, 130.0, 2.7, 0.3, 28.0),
//    Food(74, R.string.rice_brown, FoodType.FOOD, 111.0, 2.6, 0.9, 23.0),
//    Food(75, R.string.quinoa, FoodType.FOOD, 120.0, 4.4, 1.9, 21.3),
//    Food(76, R.string.bulgur, FoodType.FOOD, 83.0, 3.1, 0.2, 18.6),
//    Food(77, R.string.pasta, FoodType.FOOD, 131.0, 4.6, 0.7, 25.0),
//    Food(78, R.string.bread_white, FoodType.FOOD, 265.0, 9.0, 3.2, 49.0),
//    Food(79, R.string.bread_wholegrain, FoodType.FOOD, 247.0, 13.0, 3.4, 41.0),
//    Food(80, R.string.bread_rye, FoodType.FOOD, 259.0, 8.5, 3.3, 48.0),
//    Food(81, R.string.buckwheat_bread, FoodType.FOOD, 228.0, 7.5, 2.5, 44.0),
//
//    // 🥔 Овощи
//    Food(82, R.string.potato, FoodType.FOOD, 86.0, 1.7, 0.1, 18.2),
//    Food(83, R.string.sweet_potato, FoodType.FOOD, 86.0, 1.6, 0.1, 20.1),
//    Food(84, R.string.carrot, FoodType.FOOD, 41.0, 0.9, 0.2, 9.6),
//    Food(85, R.string.tomato, FoodType.FOOD, 18.0, 0.9, 0.2, 3.9),
//    Food(86, R.string.cucumber, FoodType.FOOD, 15.0, 0.7, 0.1, 3.6),
//    Food(87, R.string.bell_pepper, FoodType.FOOD, 27.0, 1.0, 0.3, 6.0),
//    Food(88, R.string.broccoli, FoodType.FOOD, 34.0, 2.8, 0.4, 6.6),
//    Food(89, R.string.cauliflower, FoodType.FOOD, 25.0, 2.0, 0.3, 5.0),
//    Food(90, R.string.zucchini, FoodType.FOOD, 17.0, 1.2, 0.3, 3.1),
//    Food(91, R.string.eggplant, FoodType.FOOD, 25.0, 1.0, 0.2, 5.9),
//    Food(92, R.string.onion, FoodType.FOOD, 40.0, 1.1, 0.1, 9.3),
//    Food(93, R.string.garlic, FoodType.FOOD, 149.0, 6.4, 0.5, 33.1),
//    Food(94, R.string.cabbage, FoodType.FOOD, 25.0, 1.3, 0.1, 5.8),
//    Food(95, R.string.spinach, FoodType.FOOD, 23.0, 2.9, 0.4, 3.6),
//    Food(96, R.string.lettuce, FoodType.FOOD, 15.0, 1.4, 0.2, 2.9),
//    Food(97, R.string.celery, FoodType.FOOD, 16.0, 0.7, 0.2, 3.0),
//    Food(98, R.string.mushrooms, FoodType.FOOD, 22.0, 3.1, 0.3, 3.3),
//    Food(99, R.string.green_beans, FoodType.FOOD, 31.0, 1.8, 0.1, 7.0),
//    Food(100, R.string.corn, FoodType.FOOD, 86.0, 3.3, 1.4, 19.0),
//    Food(101, R.string.peas, FoodType.FOOD, 81.0, 5.4, 0.4, 14.5),
//
//    // 🍎 Фрукты и ягоды
//    Food(102, R.string.apple, FoodType.FOOD, 52.0, 0.3, 0.2, 14.0),
//    Food(103, R.string.banana, FoodType.FOOD, 89.0, 1.1, 0.3, 22.8),
//    Food(104, R.string.orange, FoodType.FOOD, 47.0, 0.9, 0.1, 11.8),
//    Food(105, R.string.mandarin, FoodType.FOOD, 53.0, 0.8, 0.3, 13.3),
//    Food(106, R.string.lemon, FoodType.FOOD, 29.0, 1.1, 0.3, 9.3),
//    Food(107, R.string.grapefruit, FoodType.FOOD, 42.0, 0.8, 0.1, 10.7),
//    Food(108, R.string.pear, FoodType.FOOD, 57.0, 0.4, 0.1, 15.2),
//    Food(109, R.string.peach, FoodType.FOOD, 39.0, 0.9, 0.3, 9.5),
//    Food(110, R.string.apricot, FoodType.FOOD, 48.0, 1.4, 0.4, 11.1),
//    Food(111, R.string.plum, FoodType.FOOD, 46.0, 0.7, 0.3, 11.4),
//    Food(112, R.string.cherry, FoodType.FOOD, 50.0, 1.0, 0.3, 12.2),
//    Food(113, R.string.strawberry, FoodType.FOOD, 32.0, 0.7, 0.3, 7.7),
//    Food(114, R.string.raspberry, FoodType.FOOD, 52.0, 1.2, 0.7, 11.9),
//    Food(115, R.string.blueberry, FoodType.FOOD, 57.0, 0.7, 0.3, 14.5),
//    Food(116, R.string.blackberry, FoodType.FOOD, 43.0, 1.4, 0.5, 9.6),
//    Food(117, R.string.grape, FoodType.FOOD, 69.0, 0.7, 0.2, 18.0),
//    Food(118, R.string.watermelon, FoodType.FOOD, 30.0, 0.6, 0.2, 7.6),
//    Food(119, R.string.melon, FoodType.FOOD, 34.0, 0.8, 0.2, 8.2),
//    Food(120, R.string.pineapple, FoodType.FOOD, 50.0, 0.5, 0.1, 13.1),
//    Food(121, R.string.mango, FoodType.FOOD, 60.0, 0.8, 0.4, 15.0),
//    Food(122, R.string.kiwi, FoodType.FOOD, 61.0, 1.1, 0.5, 14.7),
//    Food(123, R.string.pomegranate, FoodType.FOOD, 83.0, 1.7, 1.2, 18.7),
//    Food(124, R.string.avocado, FoodType.FOOD, 160.0, 2.0, 14.7, 8.5),
//
//    // 🥜 Орехи и семена
//    Food(125, R.string.almond, FoodType.FOOD, 579.0, 21.2, 49.9, 21.7),
//    Food(126, R.string.walnut, FoodType.FOOD, 654.0, 15.2, 65.2, 13.7),
//    Food(127, R.string.peanut, FoodType.FOOD, 567.0, 25.8, 49.2, 16.1),
//    Food(128, R.string.cashew, FoodType.FOOD, 553.0, 18.2, 43.9, 30.2),
//    Food(129, R.string.hazelnut, FoodType.FOOD, 628.0, 15.0, 60.8, 16.7),
//    Food(130, R.string.pistachio, FoodType.FOOD, 560.0, 20.3, 45.4, 27.5),
//    Food(131, R.string.sunflower_seeds, FoodType.FOOD, 584.0, 20.8, 52.9, 20.0),
//    Food(132, R.string.pumpkin_seeds, FoodType.FOOD, 559.0, 30.2, 49.0, 10.7),
//    Food(133, R.string.chia_seeds, FoodType.FOOD, 486.0, 16.5, 30.7, 42.1),
//    Food(134, R.string.flax_seeds, FoodType.FOOD, 534.0, 18.3, 42.2, 28.9),
//
//    // 🥑 Бобовые
//    Food(135, R.string.lentils, FoodType.FOOD, 116.0, 9.0, 0.4, 20.0),
//    Food(136, R.string.chickpeas, FoodType.FOOD, 139.0, 7.0, 2.1, 22.0),
//    Food(137, R.string.beans_white, FoodType.FOOD, 139.0, 9.7, 0.4, 25.0),
//    Food(138, R.string.beans_red, FoodType.FOOD, 127.0, 8.7, 0.5, 22.8),
//    Food(139, R.string.soybeans, FoodType.FOOD, 173.0, 17.0, 9.0, 9.9),
//    Food(140, R.string.tofu, FoodType.FOOD, 76.0, 8.1, 4.8, 1.9),
//    Food(141, R.string.tempeh, FoodType.FOOD, 193.0, 19.0, 11.0, 9.0),
//
//    // 🍯 Масла и соусы
//    Food(142, R.string.olive_oil, FoodType.FOOD, 884.0, 0.0, 100.0, 0.0),
//    Food(143, R.string.sunflower_oil, FoodType.FOOD, 884.0, 0.0, 100.0, 0.0),
//    Food(144, R.string.coconut_oil, FoodType.FOOD, 862.0, 0.0, 100.0, 0.0),
//    Food(145, R.string.mayonnaise, FoodType.FOOD, 724.0, 0.3, 79.0, 2.6),
//    Food(146, R.string.ketchup, FoodType.FOOD, 101.0, 1.0, 0.3, 25.0),
//    Food(147, R.string.soy_sauce, FoodType.FOOD, 53.0, 8.0, 0.1, 4.9),
//    Food(148, R.string.mustard, FoodType.FOOD, 66.0, 4.4, 3.3, 5.8),
//    Food(149, R.string.bbq_sauce, FoodType.FOOD, 120.0, 0.8, 0.5, 28.0),
//
//    // 🍫 Сладости и снеки
//    Food(150, R.string.dark_chocolate, FoodType.FOOD, 598.0, 7.8, 42.7, 45.9),
//    Food(151, R.string.milk_chocolate, FoodType.FOOD, 535.0, 7.7, 29.7, 59.4),
//    Food(152, R.string.white_chocolate, FoodType.FOOD, 539.0, 5.9, 32.1, 59.2),
//    Food(153, R.string.honey, FoodType.FOOD, 304.0, 0.3, 0.0, 82.4),
//    Food(154, R.string.sugar, FoodType.FOOD, 387.0, 0.0, 0.0, 99.8),
//    Food(155, R.string.jam, FoodType.FOOD, 250.0, 0.4, 0.1, 62.0),
//    Food(156, R.string.potato_chips, FoodType.FOOD, 536.0, 6.6, 34.0, 53.0),
//    Food(157, R.string.popcorn, FoodType.FOOD, 375.0, 12.0, 4.2, 77.0),
//    Food(158, R.string.cookies, FoodType.FOOD, 450.0, 6.0, 18.0, 65.0),
//    Food(159, R.string.cake_chocolate, FoodType.FOOD, 370.0, 5.0, 15.0, 55.0),
//
//    // 🍽️ Готовые блюда и фаст-фуд
//    Food(160, R.string.porridge_milk, FoodType.FOOD, 120.0, 4.5, 3.5, 18.0),
//    Food(161, R.string.scrambled_eggs, FoodType.FOOD, 180.0, 12.0, 14.0, 1.0),
//    Food(162, R.string.omelette, FoodType.FOOD, 154.0, 10.6, 11.7, 2.2),
//    Food(163, R.string.pancakes, FoodType.FOOD, 227.0, 6.0, 9.0, 30.0),
//    Food(164, R.string.pizza_margherita, FoodType.FOOD, 266.0, 11.0, 9.0, 33.0),
//    Food(165, R.string.burger_cheeseburger, FoodType.FOOD, 303.0, 17.0, 15.0, 28.0),
//    Food(166, R.string.french_fries, FoodType.FOOD, 312.0, 3.4, 15.0, 41.0),
//    Food(167, R.string.sushi_roll, FoodType.FOOD, 150.0, 4.0, 1.0, 30.0),
//    Food(168, R.string.borscht, FoodType.FOOD, 60.0, 3.0, 2.0, 8.0),
//    Food(169, R.string.chicken_soup, FoodType.FOOD, 45.0, 5.0, 1.5, 4.0)

    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = LocalDate.now().format(formatter)
        return currentDate
    }

    override fun findFoodById(id: Int): Food? {
        return foodList.find { food -> food.id == id }
    }
}

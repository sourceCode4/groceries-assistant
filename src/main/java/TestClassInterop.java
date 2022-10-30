import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class TestClassInterop {
    public static void createUserTable() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();
//            String sql = "CREATE TABLE SHOPPINGLIST " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " ITEM           TEXT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " PRICE          FLOAT)";

            String sql =
//                    "CREATE TYPE diettype AS ENUM ('VEGAN', 'VEGETARIAN', 'PESCETARIAN','OMNIVORE');" +
                            "CREATE TABLE USERDATA " +
                            "(NAME TEXT PRIMARY KEY NOT NULL," +
                            " HEIGHT            FLOAT, " +
                            " WEIGHT            FLOAT, " +
                            " AGE               INT, " +
                            " SEX               TEXT," +
                            " CALORIES          FLOAT," +
                            " PROTEIN           FLOAT," +
                            " CARBS             FLOAT," +
                            " FATS              FLOAT," +
                            " DIET              diettype)";


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void createShoppinglist() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();


            String sql =
                    "CREATE TABLE SHOPPINGLIST " +
                            "(NAME TEXT PRIMARY KEY NOT NULL," +
                            " Brandy            INT, " +
                            " Campari           INT, " +
                            " Cognac            INT, " +
                            " Gin_young_Dutch               INT," +
                            " Gin_old_Dutch          INT," +
                            " Port_wine           INT," +
                            " Rum             INT," +
                            " Sherry              INT," +
                            " Vermouth           INT, " +
                            " Whisky           INT, " +
                            " Wine_red           INT, " +
                            " Wine_white_dry               INT," +
                            " Cider          INT," +
                            " Wine_rose           INT," +
                            " Bread_White             INT," +
                            " Bread_brown_wheat              INT," +
                            " Bread_rye           INT, " +
                            " Pasta_white_raw            INT, " +
                            " Rice_white_raw               INT," +
                            " Semolina          INT," +
                            " Flour_rice           INT," +
                            " Flour_buckwheat             INT," +
                            " Cereal              INT," +
                            " Oatmeal           INT, " +
                            " Cornstarch           INT, " +
                            " Rice_brown_raw           INT, " +
                            " Couscous_unprepared               INT," +
                            " Barley_whole_grain_raw          INT," +
                            " Wrap_Tortilla           INT," +
                            " Muesli_crunchy_plain_fruit             INT," +
                            " Quinoa_raw              INT," +
                            " Quinoa_cooked            INT, " +
                            " Cheese_Swiss_dried          INT, " +
                            " Cheese_Edam_40            INT, " +
                            " Cheese_Gouda_48_av               INT," +
                            " Cheese_20_Leidse_cumin_Fries_clove          INT," +
                            " Cheese_spread_48_full_fat           INT," +
                            " Cheese_spread_40             INT," +
                            " Cheese_spread_20              INT," +
                            " Cheese_Camembert_45           INT, " +
                            " Cheese_Brie_50           INT, " +
                            " Cheese_cottage          INT, " +
                            " Cheese_Roquefort               INT," +
                            " Cheese_Saint_Paulin_Port_Salut          INT," +
                            " Cheese_Parmesan           INT," +
                            " Cheese_cream_soft_Mon_Chou            INT," +
                            " Cheese_Limburger              INT," +
                            " Cheese_Gruyere            INT, " +
                            " Cheese_Emmenthaler           INT, " +
                            " Cheese_Cheddar            INT, " +
                            " Cheese_Bluefort               INT," +
                            " Cheese_cream_soft_Boursin          INT," +
                            " Cheese_sheep_fresh           INT," +
                            " Cheese_Kernhem_60             INT," +
                            " Cheese_Gorgonzola             INT," +
                            " Cheese_Mozzarella_made_from_cows_milk           INT, " +
                            " Cheese_Feta          INT, " +
                            " Cheese_cream_w_herbs_25_30_g_fat           INT, " +
                            " Cheese_white_45_feta_like_from_cows_milk               INT," +
                            " Cheese_Danish_Blue          INT," +
                            " Bacon           INT," +
                            " Salami             INT," +
                            " Ham_lean_grilled              INT," +
                            " Chicken            INT, " +
                            " Turkey           INT, " +
                            " Sausage            INT, " +
                            " Egg               INT," +
                            " Oil_peanut          INT," +
                            " Butter_unsalted           INT," +
                            " Oil_soy             INT," +
                            " Lard              INT," +
                            " Oil_sunflower_seed           INT, " +
                            " Oil_olive           INT, " +
                            " Margarine           INT, " +
                            " Oil_linseed               INT," +
                            " Oil_sesame          INT," +
                            " Oil_palm             INT," +
                            " Eel_raw              INT," +
                            " Herring_raw           INT, " +
                            " Mackerel_raw            INT, " +
                            " Oysters               INT," +
                            " Sardines_pilchards_in_oil_tinned          INT," +
                            " Salmon_tinned          INT," +
                            " Fish_fingers_unprepared             INT," +
                            " Cod_raw              INT," +
                            " Salmon_smoked           INT, " +
                            " Caviar           INT, " +
                            " Squid_raw          INT, " +
                            " Salmon_farmed_raw               INT," +
                            " Anchovy_in_oil_canned          INT," +
                            " Tuna_in_oil_tinned           INT," +
                            " Tuna_in_water_tinned             INT," +
                            " Tuna_raw              INT," +
                            " Tilapia_raw           INT, " +
                            " Surimi          INT, " +
                            " Apple_wo_skin_av           INT, " +
                            " Strawberries               INT," +
                            " Apricots_w_skin          INT," +
                            " Pineapple           INT," +
                            " Banana             INT," +
                            " Blueberries              INT," +
                            " Lemon           INT, " +
                            " Cranberries_fresh           INT, " +
                            " Grapes_w_skin_av          INT, " +
                            " Raspberries               INT," +
                            " Grapefruit          INT," +
                            " Cherries           INT," +
                            " Manderins            INT," +
                            " Melon_netted              INT," +
                            " Pear_wo_skin            INT, " +
                            " Peach_wo_skin           INT, " +
                            " Plums_w_skin            INT, " +
                            " Orange               INT," +
                            " Apples_dried          INT," +
                            " Prunes           INT," +
                            " Coconut_meat             INT," +
                            " Plantain_ripe_raw             INT," +
                            " Avocado           INT, " +
                            " Guava          INT, " +
                            " Lime           INT, " +
                            " Mango               INT," +
                            " Papaya          INT," +
                            " Apple_w_skin_av           INT," +
                            " Pomegranate             INT," +
                            " Figs_fresh              INT," +

                            " Kiwi_fruit_green          INT, " +
                            " Melon_water               INT," +
                            " Melon_honeydew          INT," +
                            " Nectarine           INT," +
                            " Dates_fresh            INT," +
                            " Grapes_black_w_skin              INT," +
                            " Grapes_white_w_skin            INT, " +
                            " Melon_cantaloupe           INT, " +
                            " Parsley_fresh            INT, " +
                            " Pepper_black_white               INT," +
                            " Mustard          INT," +
                            " Nutmeg_powder           INT," +
                            " Cinnamon_powder            INT," +
                            " Cumin_seed_dried             INT," +
                            " Cloves           INT, " +
                            " Chives_fresh          INT, " +
                            " Ginger_root           INT, " +
                            " Soy_sauce_salt               INT," +
                            " Basil_dried          INT," +
                            " Chilli_powder           INT," +
                            " Oregano_dried             INT," +
                            " Paprika_powder             INT," +


                            " Parsley_dried          INT, " +
                            " Rosemary_dried               INT," +
                            " Pepper_red_hot_paste          INT," +
                            " Basil_fresh           INT," +
                            " Beans_brown_canned            INT," +
                            " Beans_black_eyed_dried              INT," +
                            " Peas_split_yellow_green_dried            INT, " +
                            " Beans_kidney_red_dried           INT, " +
                            " Beans_white_canned            INT, " +
                            " Beans_kidney_red_canned               INT," +
                            " Peas_chick_canned          INT," +
                            " Beans_black_eyed_canned           INT," +
                            " Lentils_brown_canned            INT," +
                            " Beans_cannellini_canned             INT," +
                            " Beans_chilli_canned           INT, " +
                            " Beans_black_canned          INT, " +
                            " Duck_w_skin_raw           INT, " +
                            " Hare_whole_raw               INT," +
                            " Chicken_w_skin_raw          INT," +
                            " Turkey_raw           INT," +
                            " Venison_raw             INT," +
                            " Minced_beef_pork_shallow_fried             INT," +

                            " Minced_beef_kofte_Turkish_prepared          INT, " +
                            " Minced_mutton_prepared_Kofte_Turkish               INT," +
                            " Minced_beef_ball_prepared_w_egg_crumbs          INT," +
                            " Beef_rump_steak_raw           INT," +
                            " Beef_prime_rib_raw            INT," +
                            " Beef_frying_steak_raw              INT," +
                            " Minced_beef_raw            INT, " +
                            " Beef_rib_raw           INT, " +
                            " Pork_fillet_raw           INT, " +
                            " Minced_beef_pork_raw               INT," +
                            " Hamburger_raw          INT," +
                            " Veal_prime_rib_raw           INT," +
                            " Minced_lamb_raw           INT," +
                            " Lamb_chop_raw             INT," +
                            " Tempeh_fermented_soya_beans_prepared_wo_fat           INT, " +
                            " Drink_soya_natural          INT, " +
                            " Miso_soya_paste           INT, " +
                            " Seitan               INT," +
                            " Hamburger_vegetarian_unprep          INT," +
                            " Vegetarian_mincemeat_balls_unprep           INT," +
                            " Ham_vegetarian             INT," +
                            " Ice_cream_based_on_coconutmilk             INT," +

                            " Nuggets_vegetarian_unprepared          INT, " +
                            " Falafel_unprepared               INT," +
                            " Milk_chocolate_flavoured_full_fat          INT," +
                            " Yoghurt_full_fat           INT," +
                            " Milk_whole           INT," +
                            " Milk_semi_skimmed              INT," +
                            " Ice_cream_dairy_av            INT, " +
                            " Cream_sour           INT, " +
                            " Kefir           INT, " +
                            " Milk_goats_full_fat              INT," +
                            " Jelly          INT," +



                            " Lettuce_romaine_raw            INT, " +
                            " Asparagus_green_raw           INT, " +
                            " Sweet_pepper_orange_raw               INT, " +
                            " Sweet_pepper_av_raw INT," +
                            " Sweet_pepper_yellow_raw          INT," +
                            " Rocket_raw           INT," +
                            " Tomato_av_raw           INT," +
                            " Tomato_beef_raw             INT," +
                            " Tomato_cherry_raw            INT," +
                            " Tomato_vine_raw          INT, " +
                            " Carrot_bunched_raw           INT, " +
                            " Lettuce_red_raw               INT, " +
                            " Chili_pepper_raw              INT," +
                            " Tomato_sieved_pack          INT," +
                            " Capers           INT," +
                            " Tomato_sun_dried           INT," +
                            " Lettuce_av_raw             INT," +
                            " Lettuce_iceberg_raw             INT," +
                            " Kale_curly_glass            INT, " +
                            " Vegetables_pickled_Turkish_Tursu           INT, " +
                            " Cucumber_sliced_pickled_glass               INT, " +
                            " Courgettes_raw                    INT," +
                            " Broccoli_raw          INT," +
                            " Pumpkin_raw          INT," +
                            " Celery_raw          INT," +
                            " Cabbage_sauerkraut_raw             INT," +
                            " Carrot_raw_av             INT," +
                            " Cabbage_white_raw            INT, " +
                            " Chicory_raw           INT, " +
                            " Lettuce_lambs_raw              INT, " +
                            " Onions_raw                 INT," +
                            " Beans_broad_raw          INT," +
                            " Tomatoes_classic_round_raw           INT," +
                            " Spinach_raw        INT," +
                            " Beans_French_raw           INT," +
                            " Beans_runner_raw             INT," +
                            " Lettuce_butterhead_raw      INT," +
                            " Cabbage_red_raw          INT," +
                            " Rhubarb_raw         INT," +
                            " Turnip_tops_raw           INT," +
                            " Sweet_pepper_green_raw            INT," +
                            " Cucumber_wo_skin_raw            INT," +
                            " Cabbage_green_raw          INT, " +
                            " Peas_raw         INT, " +
                            " Cabbage_Chinese_raw            INT, " +
                            " Mushroom_raw             INT," +
                            " Mushrooms_chanterelle_raw          INT," +
                            " Cauliflower_raw           INT," +
                            " Beetroot_raw           INT," +
                            " Aubergine_eggplant_raw             INT," +
                            " Asparagus_white_raw           INT," +
                            " Endive_raw            INT, " +
                            " Fruit_spread           INT, " +
                            " Candy_mix               INT, " +
                            " Chocolate_extra_dark                   INT," +
                            " Turkish_Delight          INT," +
                            " Marshmallows      INT," +
                            " Chocolate_w_liqueur           INT, " +
                            " Chocolate_milk_w_hazelnuts              INT, " +
                            " Candybar_Nuts                INT," +
                            " Candybar_Mars          INT," +
                            " Chewing_gum           INT," +
                            " Jam        INT," +
                            " Honey           INT," +
                            " Coloured_sprinkles_fruit_flavoured            INT," +
                            " Spread_chocolate_hazelnut            INT," +
                            " Chocolate_dark           INT, " +
                            " Chocolate_milk             INT, " +
                            " Sugar_castor_white              INT," +
                            " Sugar_castor_brown      INT," +
                            " Soup_main_course_w_legumes_and_meat           INT," +
                            " Soup_main_course_w_legumes_wo_meat           INT," +
                            " Soup_thickened_w_meat_beef_chicken             INT," +
                            " Soup_thickened_w_vegetables             INT," +
                            " Soup_clear_w_meat_beef_chicken_vegetables_and_noodles            INT, " +
                            " Soup_clear_w_meat_beef_chicken_and_vegetables           INT, " +
                            " Soup_clear_w_meat_beef_chicken_and_noodles               INT, " +
                            " Soup_clear_w_vegetables                    INT," +
                            " Soup_clear_w_meat_beef_chicken          INT," +
                            " Soup_clear_w_vegetables_and_noodles          INT," +
                            " Crisps_tortilla_unflavoured          INT," +
                            " Popcorn_plain_popped_wo_oil             INT," +
                            " Prawn_crackers_natural             INT," +
                            " Pretzel_sticks            INT, " +
                            " Biscuit_salted          INT, " +
                            " Crisps_potato_av              INT, " +
                            " Sauce_garlic_30_40_oil                INT," +
                            " Sauce_chilli          INT," +
                            " Pesto_red           INT," +
                            " Sauce_soy        INT," +
                            " Pesto_green         INT," +
                            " Mango_chutney           INT," +
                            " Ketchup_tomato          INT," +
                            " Mayonnaise          INT," +
                            " Sauce_cocktail_party_table_25_oil         INT," +
                            " Sauce_barbecue           INT," +
                            " Sesame_paste_tahin_salt_added            INT," +
                            " Sandwich_spread_original            INT," +
                            " Peanut_butter_w_peanut_pieces          INT, " +
                            " Peanut_butter         INT, " +
                            " Gnocchi_unprepared            INT, " +
                            " Potato_sweet_raw             INT," +
                            " Yam_raw          INT," +
                            " Taro_raw           INT," +
                            " Potatoes_old_raw           INT," +
                            " Potatoes_new_raw             INT," +
                            " Potatoes_raw           INT," +
                            " Wafer_galette            INT, " +
                            " Muesli_bar           INT, " +
                            " Biscuit               INT, " +
                            " Chia_seeds_dried                   INT," +
                            " Peanuts_sugar_coated          INT," +
                            " Pine_nuts          INT," +
                            " Peanuts_dry_roasted      INT," +
                            " Nuts_mixed_salted            INT," +
                            " Pistachio_nuts_salted           INT," +
                            " Pecan_nuts_unroasted_unsalted            INT, " +
                            " Peanuts_salted           INT, " +
                            " Sunflower_seeds              INT, " +
                            " Nuts_mixed_unsalted                INT," +
                            " Walnuts_unsalted         INT," +
                            " Mixed_nuts_and_raisins           INT," +
                            " Peanuts_unsalted        INT," +
                            " Brazil_nuts_unsalted           INT," +
                            " Chestnuts_raw            INT," +
                            " Hazelnuts_unsalted            INT," +

                            " Cashew_nuts_unsalted          INT," +
                            " Almonds_blanched_unsalted         INT," +
                            " Energy_drink_Red_Bull_sugarfree             INT," +
                            " Energy_drink_Red_Bull            INT," +
                            " Coffee_instant_powder            INT, " +
                            " Juice_multifruit           INT, " +
                            " Coffee_cappuccino_instant_powder              INT, " +
                            " Tea_herbal_instant_sweetend_prepared                 INT," +
                            " Ice_tea_light          INT," +
                            " Juice_orange_w_pulp          INT," +
                            " Water_av       INT," +
                            " Juice_carrot           INT," +
                            " Cola_light_soft_drink_w_caffeine            INT," +
                            " Juice_pineapple      INT," +
                            " Juice_tomato_vegetable_low_sodium          INT," +
                            " Juice_tomato_vegetable         INT," +
                            " Juice_lemon_fresh           INT," +
                            " Juice_pear           INT," +
                            " Juice_beetroot            INT," +
                            " Mineral_water_sparkling_and_not_sparkling_av         INT, " +
                            " Juice_grapefruit         INT, " +
                            " Water_100_mg_calcium_p_litre            INT, " +
                            " Water_50_100_mg_calcium_p_litre             INT," +
                            " Water_0_50_mg_calcium_p_litre          INT," +
                            " Tomato_juice           INT," +
                            " Fruit_juice_drink_raspberry           INT," +
                            " Juice_grape             INT," +
                            " Juice_apple          INT," +
                            " Cocoa_powder_low_fat            INT, " +
                            " Vinegar_Balsamic           INT, " +
                            " Salt_sea               INT, " +
                            " Vinegar                   INT," +
                            " Ice_cream_dairy_non_dairy_cornet_w_fruit          INT," +
                            " Ice_cream_dairy_w_fruit          INT," +
                            " Ice_cream_dairy_w_vegetal_fat_vanilla_flavoured     INT," +
                            " Ice_cream_dairy_vanilla_flavoured            INT," +
                            " Ice_cream_chocolate           INT," +
                            " Ice_cream_dairy_w_caramel_and_nuts            INT, " +
                            " Ice_cream_dairy_stracciatella           INT)";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void createShoppinglogs() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();
//            String sql = "CREATE TABLE SHOPPINGLIST " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " ITEM           TEXT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " PRICE          FLOAT)";

            String sql =
                    "CREATE TABLE SHOPPINGLOGS " +
                            "(NAME TEXT PRIMARY KEY NOT NULL," +
                            " Brandy            INT, " +
                            " Campari           INT, " +
                            " Cognac            INT, " +
                            " Gin_young_Dutch               INT," +
                            " Gin_old_Dutch          INT," +
                            " Port_wine           INT," +
                            " Rum             INT," +
                            " Sherry              INT," +
                            " Vermouth           INT, " +
                            " Whisky           INT, " +
                            " Wine_red           INT, " +
                            " Wine_white_dry               INT," +
                            " Cider          INT," +
                            " Wine_rose           INT," +
                            " Bread_White             INT," +
                            " Bread_brown_wheat              INT," +
                            " Bread_rye           INT, " +
                            " Pasta_white_raw            INT, " +
                            " Rice_white_raw               INT," +
                            " Semolina          INT," +
                            " Flour_rice           INT," +
                            " Flour_buckwheat             INT," +
                            " Cereal              INT," +
                            " Oatmeal           INT, " +
                            " Cornstarch           INT, " +
                            " Rice_brown_raw           INT, " +
                            " Couscous_unprepared               INT," +
                            " Barley_whole_grain_raw          INT," +
                            " Wrap_Tortilla           INT," +
                            " Muesli_crunchy_plain_fruit             INT," +
                            " Quinoa_raw              INT," +
                            " Quinoa_cooked            INT, " +
                            " Cheese_Swiss_dried          INT, " +
                            " Cheese_Edam_40            INT, " +
                            " Cheese_Gouda_48_av               INT," +
                            " Cheese_20_Leidse_cumin_Fries_clove          INT," +
                            " Cheese_spread_48_full_fat           INT," +
                            " Cheese_spread_40             INT," +
                            " Cheese_spread_20              INT," +
                            " Cheese_Camembert_45           INT, " +
                            " Cheese_Brie_50           INT, " +
                            " Cheese_cottage          INT, " +
                            " Cheese_Roquefort               INT," +
                            " Cheese_Saint_Paulin_Port_Salut          INT," +
                            " Cheese_Parmesan           INT," +
                            " Cheese_cream_soft_Mon_Chou            INT," +
                            " Cheese_Limburger              INT," +
                            " Cheese_Gruyere            INT, " +
                            " Cheese_Emmenthaler           INT, " +
                            " Cheese_Cheddar            INT, " +
                            " Cheese_Bluefort               INT," +
                            " Cheese_cream_soft_Boursin          INT," +
                            " Cheese_sheep_fresh           INT," +
                            " Cheese_Kernhem_60             INT," +
                            " Cheese_Gorgonzola             INT," +
                            " Cheese_Mozzarella_made_from_cows_milk           INT, " +
                            " Cheese_Feta          INT, " +
                            " Cheese_cream_w_herbs_25_30_g_fat           INT, " +
                            " Cheese_white_45_feta_like_from_cows_milk               INT," +
                            " Cheese_Danish_Blue          INT," +
                            " Bacon           INT," +
                            " Salami             INT," +
                            " Ham_lean_grilled              INT," +
                            " Chicken            INT, " +
                            " Turkey           INT, " +
                            " Sausage            INT, " +
                            " Egg               INT," +
                            " Oil_peanut          INT," +
                            " Butter_unsalted           INT," +
                            " Oil_soy             INT," +
                            " Lard              INT," +
                            " Oil_sunflower_seed           INT, " +
                            " Oil_olive           INT, " +
                            " Margarine           INT, " +
                            " Oil_linseed               INT," +
                            " Oil_sesame          INT," +
                            " Oil_palm             INT," +
                            " Eel_raw              INT," +
                            " Herring_raw           INT, " +
                            " Mackerel_raw            INT, " +
                            " Oysters               INT," +
                            " Sardines_pilchards_in_oil_tinned          INT," +
                            " Salmon_tinned          INT," +
                            " Fish_fingers_unprepared             INT," +
                            " Cod_raw              INT," +
                            " Salmon_smoked           INT, " +
                            " Caviar           INT, " +
                            " Squid_raw          INT, " +
                            " Salmon_farmed_raw               INT," +
                            " Anchovy_in_oil_canned          INT," +
                            " Tuna_in_oil_tinned           INT," +
                            " Tuna_in_water_tinned             INT," +
                            " Tuna_raw              INT," +
                            " Tilapia_raw           INT, " +
                            " Surimi          INT, " +
                            " Apple_wo_skin_av           INT, " +
                            " Strawberries               INT," +
                            " Apricots_w_skin          INT," +
                            " Pineapple           INT," +
                            " Banana             INT," +
                            " Blueberries              INT," +
                            " Lemon           INT, " +
                            " Cranberries_fresh           INT, " +
                            " Grapes_w_skin_av          INT, " +
                            " Raspberries               INT," +
                            " Grapefruit          INT," +
                            " Cherries           INT," +
                            " Manderins            INT," +
                            " Melon_netted              INT," +
                            " Pear_wo_skin            INT, " +
                            " Peach_wo_skin           INT, " +
                            " Plums_w_skin            INT, " +
                            " Orange               INT," +
                            " Apples_dried          INT," +
                            " Prunes           INT," +
                            " Coconut_meat             INT," +
                            " Plantain_ripe_raw             INT," +
                            " Avocado           INT, " +
                            " Guava          INT, " +
                            " Lime           INT, " +
                            " Mango               INT," +
                            " Papaya          INT," +
                            " Apple_w_skin_av           INT," +
                            " Pomegranate             INT," +
                            " Figs_fresh              INT," +

                            " Kiwi_fruit_green          INT, " +
                            " Melon_water               INT," +
                            " Melon_honeydew          INT," +
                            " Nectarine           INT," +
                            " Dates_fresh            INT," +
                            " Grapes_black_w_skin              INT," +
                            " Grapes_white_w_skin            INT, " +
                            " Melon_cantaloupe           INT, " +
                            " Parsley_fresh            INT, " +
                            " Pepper_black_white               INT," +
                            " Mustard          INT," +
                            " Nutmeg_powder           INT," +
                            " Cinnamon_powder            INT," +
                            " Cumin_seed_dried             INT," +
                            " Cloves           INT, " +
                            " Chives_fresh          INT, " +
                            " Ginger_root           INT, " +
                            " Soy_sauce_salt               INT," +
                            " Basil_dried          INT," +
                            " Chilli_powder           INT," +
                            " Oregano_dried             INT," +
                            " Paprika_powder             INT," +


                            " Parsley_dried          INT, " +
                            " Rosemary_dried               INT," +
                            " Pepper_red_hot_paste          INT," +
                            " Basil_fresh           INT," +
                            " Beans_brown_canned            INT," +
                            " Beans_black_eyed_dried              INT," +
                            " Peas_split_yellow_green_dried            INT, " +
                            " Beans_kidney_red_dried           INT, " +
                            " Beans_white_canned            INT, " +
                            " Beans_kidney_red_canned               INT," +
                            " Peas_chick_canned          INT," +
                            " Beans_black_eyed_canned           INT," +
                            " Lentils_brown_canned            INT," +
                            " Beans_cannellini_canned             INT," +
                            " Beans_chilli_canned           INT, " +
                            " Beans_black_canned          INT, " +
                            " Duck_w_skin_raw           INT, " +
                            " Hare_whole_raw               INT," +
                            " Chicken_w_skin_raw          INT," +
                            " Turkey_raw           INT," +
                            " Venison_raw             INT," +
                            " Minced_beef_pork_shallow_fried             INT," +

                            " Minced_beef_kofte_Turkish_prepared          INT, " +
                            " Minced_mutton_prepared_Kofte_Turkish               INT," +
                            " Minced_beef_ball_prepared_w_egg_crumbs          INT," +
                            " Beef_rump_steak_raw           INT," +
                            " Beef_prime_rib_raw            INT," +
                            " Beef_frying_steak_raw              INT," +
                            " Minced_beef_raw            INT, " +
                            " Beef_rib_raw           INT, " +
                            " Pork_fillet_raw           INT, " +
                            " Minced_beef_pork_raw               INT," +
                            " Hamburger_raw          INT," +
                            " Veal_prime_rib_raw           INT," +
                            " Minced_lamb_raw           INT," +
                            " Lamb_chop_raw             INT," +
                            " Tempeh_fermented_soya_beans_prepared_wo_fat           INT, " +
                            " Drink_soya_natural          INT, " +
                            " Miso_soya_paste           INT, " +
                            " Seitan               INT," +
                            " Hamburger_vegetarian_unprep          INT," +
                            " Vegetarian_mincemeat_balls_unprep           INT," +
                            " Ham_vegetarian             INT," +
                            " Ice_cream_based_on_coconutmilk             INT," +

                            " Nuggets_vegetarian_unprepared          INT, " +
                            " Falafel_unprepared               INT," +
                            " Milk_chocolate_flavoured_full_fat          INT," +
                            " Yoghurt_full_fat           INT," +
                            " Milk_whole           INT," +
                            " Milk_semi_skimmed              INT," +
                            " Ice_cream_dairy_av            INT, " +
                            " Cream_sour           INT, " +
                            " Kefir           INT, " +
                            " Milk_goats_full_fat              INT," +
                            " Jelly          INT," +



                            " Lettuce_romaine_raw            INT, " +
                            " Asparagus_green_raw           INT, " +
                            " Sweet_pepper_orange_raw               INT, " +
                            " Sweet_pepper_av_raw INT," +
                            " Sweet_pepper_yellow_raw          INT," +
                            " Rocket_raw           INT," +
                            " Tomato_av_raw           INT," +
                            " Tomato_beef_raw             INT," +
                            " Tomato_cherry_raw            INT," +
                            " Tomato_vine_raw          INT, " +
                            " Carrot_bunched_raw           INT, " +
                            " Lettuce_red_raw               INT, " +
                            " Chili_pepper_raw              INT," +
                            " Tomato_sieved_pack          INT," +
                            " Capers           INT," +
                            " Tomato_sun_dried           INT," +
                            " Lettuce_av_raw             INT," +
                            " Lettuce_iceberg_raw             INT," +
                            " Kale_curly_glass            INT, " +
                            " Vegetables_pickled_Turkish_Tursu           INT, " +
                            " Cucumber_sliced_pickled_glass               INT, " +
                            " Courgettes_raw                    INT," +
                            " Broccoli_raw          INT," +
                            " Pumpkin_raw          INT," +
                            " Celery_raw          INT," +
                            " Cabbage_sauerkraut_raw             INT," +
                            " Carrot_raw_av             INT," +
                            " Cabbage_white_raw            INT, " +
                            " Chicory_raw           INT, " +
                            " Lettuce_lambs_raw              INT, " +
                            " Onions_raw                 INT," +
                            " Beans_broad_raw          INT," +
                            " Tomatoes_classic_round_raw           INT," +
                            " Spinach_raw        INT," +
                            " Beans_French_raw           INT," +
                            " Beans_runner_raw             INT," +
                            " Lettuce_butterhead_raw      INT," +
                            " Cabbage_red_raw          INT," +
                            " Rhubarb_raw         INT," +
                            " Turnip_tops_raw           INT," +
                            " Sweet_pepper_green_raw            INT," +
                            " Cucumber_wo_skin_raw            INT," +
                            " Cabbage_green_raw          INT, " +
                            " Peas_raw         INT, " +
                            " Cabbage_Chinese_raw            INT, " +
                            " Mushroom_raw             INT," +
                            " Mushrooms_chanterelle_raw          INT," +
                            " Cauliflower_raw           INT," +
                            " Beetroot_raw           INT," +
                            " Aubergine_eggplant_raw             INT," +
                            " Asparagus_white_raw           INT," +
                            " Endive_raw            INT, " +
                            " Fruit_spread           INT, " +
                            " Candy_mix               INT, " +
                            " Chocolate_extra_dark                   INT," +
                            " Turkish_Delight          INT," +
                            " Marshmallows      INT," +
                            " Chocolate_w_liqueur           INT, " +
                            " Chocolate_milk_w_hazelnuts              INT, " +
                            " Candybar_Nuts                INT," +
                            " Candybar_Mars          INT," +
                            " Chewing_gum           INT," +
                            " Jam        INT," +
                            " Honey           INT," +
                            " Coloured_sprinkles_fruit_flavoured            INT," +
                            " Spread_chocolate_hazelnut            INT," +
                            " Chocolate_dark           INT, " +
                            " Chocolate_milk             INT, " +
                            " Sugar_castor_white              INT," +
                            " Sugar_castor_brown      INT," +
                            " Soup_main_course_w_legumes_and_meat           INT," +
                            " Soup_main_course_w_legumes_wo_meat           INT," +
                            " Soup_thickened_w_meat_beef_chicken             INT," +
                            " Soup_thickened_w_vegetables             INT," +
                            " Soup_clear_w_meat_beef_chicken_vegetables_and_noodles            INT, " +
                            " Soup_clear_w_meat_beef_chicken_and_vegetables           INT, " +
                            " Soup_clear_w_meat_beef_chicken_and_noodles               INT, " +
                            " Soup_clear_w_vegetables                    INT," +
                            " Soup_clear_w_meat_beef_chicken          INT," +
                            " Soup_clear_w_vegetables_and_noodles          INT," +
                            " Crisps_tortilla_unflavoured          INT," +
                            " Popcorn_plain_popped_wo_oil             INT," +
                            " Prawn_crackers_natural             INT," +
                            " Pretzel_sticks            INT, " +
                            " Biscuit_salted          INT, " +
                            " Crisps_potato_av              INT, " +
                            " Sauce_garlic_30_40_oil                INT," +
                            " Sauce_chilli          INT," +
                            " Pesto_red           INT," +
                            " Sauce_soy        INT," +
                            " Pesto_green         INT," +
                            " Mango_chutney           INT," +
                            " Ketchup_tomato          INT," +
                            " Mayonnaise          INT," +
                            " Sauce_cocktail_party_table_25_oil         INT," +
                            " Sauce_barbecue           INT," +
                            " Sesame_paste_tahin_salt_added            INT," +
                            " Sandwich_spread_original            INT," +
                            " Peanut_butter_w_peanut_pieces          INT, " +
                            " Peanut_butter         INT, " +
                            " Gnocchi_unprepared            INT, " +
                            " Potato_sweet_raw             INT," +
                            " Yam_raw          INT," +
                            " Taro_raw           INT," +
                            " Potatoes_old_raw           INT," +
                            " Potatoes_new_raw             INT," +
                            " Potatoes_raw           INT," +
                            " Wafer_galette            INT, " +
                            " Muesli_bar           INT, " +
                            " Biscuit               INT, " +
                            " Chia_seeds_dried                   INT," +
                            " Peanuts_sugar_coated          INT," +
                            " Pine_nuts          INT," +
                            " Peanuts_dry_roasted      INT," +
                            " Nuts_mixed_salted            INT," +
                            " Pistachio_nuts_salted           INT," +
                            " Pecan_nuts_unroasted_unsalted            INT, " +
                            " Peanuts_salted           INT, " +
                            " Sunflower_seeds              INT, " +
                            " Nuts_mixed_unsalted                INT," +
                            " Walnuts_unsalted         INT," +
                            " Mixed_nuts_and_raisins           INT," +
                            " Peanuts_unsalted        INT," +
                            " Brazil_nuts_unsalted           INT," +
                            " Chestnuts_raw            INT," +
                            " Hazelnuts_unsalted            INT," +

                            " Cashew_nuts_unsalted          INT," +
                            " Almonds_blanched_unsalted         INT," +
                            " Energy_drink_Red_Bull_sugarfree             INT," +
                            " Energy_drink_Red_Bull            INT," +
                            " Coffee_instant_powder            INT, " +
                            " Juice_multifruit           INT, " +
                            " Coffee_cappuccino_instant_powder              INT, " +
                            " Tea_herbal_instant_sweetend_prepared                 INT," +
                            " Ice_tea_light          INT," +
                            " Juice_orange_w_pulp          INT," +
                            " Water_av       INT," +
                            " Juice_carrot           INT," +
                            " Cola_light_soft_drink_w_caffeine            INT," +
                            " Juice_pineapple      INT," +
                            " Juice_tomato_vegetable_low_sodium          INT," +
                            " Juice_tomato_vegetable         INT," +
                            " Juice_lemon_fresh           INT," +
                            " Juice_pear           INT," +
                            " Juice_beetroot            INT," +
                            " Mineral_water_sparkling_and_not_sparkling_av         INT, " +
                            " Juice_grapefruit         INT, " +
                            " Water_100_mg_calcium_p_litre            INT, " +
                            " Water_50_100_mg_calcium_p_litre             INT," +
                            " Water_0_50_mg_calcium_p_litre          INT," +
                            " Tomato_juice           INT," +
                            " Fruit_juice_drink_raspberry           INT," +
                            " Juice_grape             INT," +
                            " Juice_apple          INT," +
                            " Cocoa_powder_low_fat            INT, " +
                            " Vinegar_Balsamic           INT, " +
                            " Salt_sea               INT, " +
                            " Vinegar                   INT," +
                            " Ice_cream_dairy_non_dairy_cornet_w_fruit          INT," +
                            " Ice_cream_dairy_w_fruit          INT," +
                            " Ice_cream_dairy_w_vegetal_fat_vanilla_flavoured     INT," +
                            " Ice_cream_dairy_vanilla_flavoured            INT," +
                            " Ice_cream_chocolate           INT," +
                            " Ice_cream_dairy_w_caramel_and_nuts            INT, " +
                            " Ice_cream_dairy_stracciatella           INT)";


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void inserter() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "407427838";

        String csvFilePath = "C:\\Users\\arsla\\Desktop\\data.csv";

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO THEFOOD (Category, EnglishName, NEVOCode, EnergyKJ, EnergyKCAL) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                System.out.println(lineText);
                String[] data = lineText.split(",");
                System.out.println(data);
                String Category = data[2];
                String EnglishName = data[5];
                String NEVOCode = data[3];
                String EnergyKJ = data[11];
                String EnergyKCAL = data[12];

                statement.setString(1, Category);
                statement.setString(2, EnglishName);

                statement.setString(3, NEVOCode);
                statement.setString(4, EnergyKJ);

                statement.setString(5, EnergyKCAL);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}

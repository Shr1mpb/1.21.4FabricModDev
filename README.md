*更新时间： 2025.2.16*

# 更新日志 (自2025.1.23)

>### 2025.2.16 - 更新了创建自定义事件的指引
> 以`item/custom`下的`HammerItem`的破坏3x3方块为例
> 
> 现在可以跟随README.md的`自定义事件`指引来实现自定义事件了

>### 2025.1.26 - 更新了创建自定义工具的相关方法
> `ModItems`类中新增`registerToolItem`方法用于注册自定义工具；新增ModMaterial类定义原材料
>
> 现在可以仿照heaven_sword的相关代码，跟着README.md的指引来创建自定义的工具了
> 
> 修复了材质放置位置不正确的问题，改进了一些注释信息

>### 2025.1.25 - 更新了tags的相关类
> 加入util/ModTags用于创建自定义标签，并加入了ModItemTagProvider类
> 
> 现在可以仿照MagicBlock的相关代码来使用自定义标签了

>### 2025.1.23 - fabric从0.114.2换成0.115.0;文件调整到client
>
> 更新了5个nonblock-block，启用了DataGenerator功能，项目的结构有很大变化;
> 
> 更新了一个release，可以看没有用datagen时的代码;
> 
> 现在可以仿照HEAVEN_LAMP_BLOCK创建双状态的方块了;
> 
> 现在可以仿照chisel创建自定义的data component并使用了

## 介绍

这里是Shr1mp的MineCraft Fabric模组开发学习

如果你看完了这篇README，那么你也可以入门Minecraft的Fabric模组开发(1.21.2+)的简单功能，我相信这些功能足以帮你实现类似 Bilibili 上的 "你说我做" 系列节目。方法就是观看这位博主在Youtube上的视频(因为那个视频只适用于1.21.2-,很多方法都无法正常使用了)，并来我这里查看我封装好的方法。或者你也可以自己封装自己的方法，或者使用原生的Fabric API。
我个人还是感觉把原生的方法都封装一下，编程体验会大大提高，也会巩固一些编程语言知识，提高代码水平。

**这里我用的版本为Java 1.21.4**

在这里你可以**套用我封装好的各种方法**(例如创建物品、方块和物品栏等)。

你可以直接去对应的类中寻找(**推荐**)。

_如果你需要帮助，我的邮箱是1205874457@qq.com(常用) / chnrzh2004@gmail.com(不常用)_ 

---

## 初衷：

* 看到网上很多模组教学都是之前的，1.21.4的Fabric API发生了较大的变化，之前的注册物品和方块的功能可能都需要被改进，即便是在Fabric Wiki上也有没有的信息，需要自己挖掘。
* 我这边跟着Youtube博主[Modding by Kaupenjoe](https://www.youtube.com/@ModdingByKaupenjoe) 学习，并**自己封装**了很多新版本的Fabric API的方法，让我能**快速的注册物品、注册方块或修改对应的属性等等。**
* 我的仓库这几天会持续更新，跟着旧版本的教学走一次，如果无法成功，我会找解决方法，之后封装好我的函数。**每个类或函数的用法我已经写在了对应的java类中。**


## Tips/食用方法/简易教程：

无论如何，我都建议你去查看这位博主的视频[Modding by Kaupenjoe](https://www.youtube.com/@ModdingByKaupenjoe)，有初中的英文水平就能看懂，他的视频长达11小时，涵盖面广，教学详细。

在遇到不生效的情况时，可以来这里查找解决方案，或者使用我封装好的方法。你也可以去Fabric开发者的社区、论坛或是[Fabric Wiki](https://wiki.fabricmc.net/start)寻找解决方法。

开发指引/本仓库使用方法 
---
学习时，本仓库也是渐进的。release里有没有使用datagen功能时的代码可以用作参考。你现在看到的仓库中的代码都是全新的。

* **创建仓库**：去Fabric API官网使用生成器。需要注意的是，如果你要使用datagen的功能，需要把相关选项打勾。


* **提交记录**中的Init就是有对API进行封装的提交，Perf或者Fix就是对这些东西的改进或修复


* `resource/assets/【modid】`中是添加**材质和模型**的地方。就Item举例而言，三层结构：items -> models -> textures，仿照书写即可。对于方块，则是blockstates -> models -> textures。使用datagen功能以后，前两层都不需要了，fabric会自己生成对应的json文件。


* **自定义物品(创造超脱原版的特定功能的新物品)**：参照`src/client/java/com/shr1mp4zh/fmod/item/custom/ChiselItem`类，在这个类上有一定的注释说明;然后，去到ModItems里用我封装的`registerCustomItem()`方法注册这个物品。原理是重写原版的Item类，它可以帮助你实现很多新的功能，或者实现你从小就想实现的梦想。
 Item类也有很多子类，有原版中实现各种各样功能的类供你参考。选中Item，使用Ctrl+H即可查看(IDEA中)。


* **自定义合成配方** ：
  对于`resource/data/【modid】/recipe`中的配方，包括合成、冶炼等，你可以在`External Libraries`中找到原版Minecraft中的一些配方，仿照他们，就能得到很不错的结果。例如对于九个Slot都一样的配方(例如合成xxx块)，在新版本中和旧版本不一样，需要仔细甄别。我也强烈建议你多使用这种方法查找解决方法，或者达成自己想要的目标(当然是你得对原版物品的机制熟悉，haha)


* **自定义方块**：_与自定义物品相似_，参照`src/client/java/com/shr1mp4zh/fmod/block/custom/MagicBlock`，然后再将它通过`registerCustomBlock()`方法注册到游戏中。

要让创建的方块被**破坏后有掉落物**或**设置开采等级**，至少需要两步，下面是具体步骤。

>1. 未使用datagen：首先需要在`resource/data/tags/block/mineable`的对应的工具中添加相应的方块，如果要设置开采等级，需要在上一级的needs_XXX_tool中添加；然后，在`resource/data/【modid】/loot_table/blocks`中添加json文件定义其掉落的物品(掉落自己本身的方块的json文件都长得差不多，参照此文件夹中的first_block.json即可；对于矿石类型的方块，还有附魔、掉落物配置之类，相对复杂，参考second_block.json)。
>2. 使用了datagen：在ModBlockTagProvider中添加对应的mineable或者needsXXX标签，然后在ModLootTableProvider中配置掉落物。仿照书写即可。

* **自定义食物**：参考`src/client/java/com/shr1mpfzh/fmod/item/ModFoodComponents`中我封装的方法与创建的`CAULIFLOWER`属性，创建完成之后要去ModItems中使用`registerCustomFood()`注册即可，注册时留空药效相关的属性。

* **自定义食物药效**：注意：食物添加药水效果的方法与1.21.2-有所不同，需要先创建普通的FoodComponent，然后去同目录下的`ModConsumableComponents`中创建药水效果，最后在ModItems注册时加入药水效果，这里已经封装到了ModItems的`registerCustomFood()`方法中。可以参考CAULIFLOWER的创建。


* **注册燃料/堆肥物品**：注册一个新的物品后，在`src/client/java/com/shr1mpfzh/fmod/item/ModItems`下的`registerFuels()`方法的lambda表达式中添加即可;堆肥物品类似，在`registerCompostingChance()`方法中。


* **设置物品发光和物品稀有度**：在`createDefaultItemSettings()`方法后使用`.component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)`;同理，使用`.rarity(Rarity.XXX)`可以设置物品稀有度(会影响物品名字的颜色)。


* **设置物品提示**：参考`src/client/java/com/shr1mp4zh/fmod/block/custom/MagicBlock`中的`appendToolTip()`。方法就是写一个物品类的子类，并且重写其appendToolTip()方法。此外，还可以添加`if(Screen.hasShiftDown())`判断来动态添加提示内容。


* **关于自定义标签(CustomTags)**：参考MagicBlock类中的onSteppedOn方法中的使用。自定义标签的作用是，能把特定的物品配置到 json 文件中，然后在代码中用一行就可以从文件中检索，比起配置到java类中要清爽很多。非必须，但建议学习。感兴趣可以看一下原视频的 [第10集 CustomTags](https://www.youtube.com/watch?v=lVpV3B3yFsg) 。


* **关于数据生成(DataGeneration)**：(建议必须学习)数据生成的用法就是在`java/com/shr1mp4zh/fmod/datagen`中。开发简化了很多步骤，但是还有小部分使用json来配置。作用是：使用新的数据生成类，可以允许你不写很多的json文件(例如，`resources`文件夹下的`assets/shr1mpfmod/blockstates&items&models`、`data/shr1mpfmod/loot_table&recipe`,还有`mineable`文件夹中配置的可以采掘的工具)，只留下textures就能把物品的模型渲染，并且可以在程序里配置配方和掉落物。注意，在你使用了datagen后，每次有新的datagen的时候你必须运行Data Generation脚本。有些文件是会冲突的，你必须妥善处理好这些冲突。感兴趣可以看一下原视频的 [第11集 DataGenerationSetup](https://www.youtube.com/watch?v=ELHvhvuGF3U&list=PLKGarocXCE1H_HxOYihQMq0mlpqiUJj4L&index=11)


* **非方块的方块(如按钮、半砖、楼梯等)**：这里都用了`registerNonBlockBlock()`，写了FIRST_STAIR、FIRST_SLAB、FIRST_BUTTON、FIRST_FENCE、FIRST_WALL作为示例。注意，这里的这五种NonBlock-Blocks部分使用了Datagen(因为NonBlock-Blocks的json配置实在太过复杂;使用datagen可以让这些方块直接基于first_block渲染其模型)。如果你的datagen没有生效，请检查是否在build.gradle中开启：需要手动设置请查阅 [FabricAPI Datagen Setup](https://wiki.fabricmc.net/zh_cn:tutorial:datagen_setup)。生成后的文件在main/generated下。
注意：FabricModelProvider类需要在client端中引用，这里把所有的Provider都放在client下了。此外，_**没有用datagen的方块也会被自动创建一些json文件**_，相关内容注释写在了`client/java/com/shr1mp4zh/fmod/Shr1mpfmodDataGeneratorClient.java`里。


* **DataComponent(数据容器)**：正如其名，就是存储数据的容器。它可以把数据存储在某个物品的栈中，让某个物品拥有独属于自己的一条“属性”。这里创建了一个coordinates容器存储坐标并引入了chisel中。参考`src/client/java/com/shr1mp4zh/fmod/component/ModDataComponentTypes.java`和chisel的相关代码。


* **自定义工具**：分三步:
首先在`ModMaterials下的ModToolMaterials类`中创建对应的原材料(包括创建原材料构造器里创建的INCORRECT标签和REPAIRS标签)，注意1.20.2+的材料创建方式与视频不一致；
然后，在`ModItems`类中调用对应的`registerToolItem()`方法(这个方法是我封装好的)传入对应的参数；
最后，在ModModelProvider中使用datagen创建对应的json文件，并把材质放在对应的位置即可。
别忘了创建合成配方和配置修复用的物品(参考`REPAIRS_HEAVEN_ARMORS`)；
**要设置工具的采掘等级**，需要使用`needs_xxx_tool`标签并向内添加其他标签(参考`needs_heaven_tool`，比如这里设置它比钻石高一级，即可以采掘需要钻石工具的方块，注意`addTag()`方法需要你在本mod中有对应的json文件)。
**要让工具对应正常的附魔**，需要将这些工具添加到对应工具Tag中(详见ModItemTagProvider)。


* **自定义事件**：以`item/custom`下的`HammerItem`的破坏3x3方块为例，创建一个HammerItem类并添加了一些要用的方法(当然也可以把这个方法放在别处，此处这个方法指获取破坏的方块)，之后在`ModItems`中注册该物品；
要实现破坏3x3方块，在`events`包下创建了新的`HammerUsageEvent`类并在`Shr1mpfmodClient`中注册了该类，在这个类中要写一些与破坏相关的逻辑，这样以来就创建好了。
---

下面是一些我的废话：

###### 我在2013年左右接触了Minecraft，那会刚上小学没多久，被这个神奇的世界吸引。之后我小学包括初中的绝大部分时间都在这款游戏上度过，只不过那会玩的都不是正版，都是国内的一些盗版服务器。
###### 之后我也有一段自己开服的经历，这个游戏可谓是我除了Counter-Strike(可能比它还长)游玩时间最长的游戏。
###### 上了大学后，当我意识到我认识它已经过去了12年，我才知道我没有自己一个人通关过任何一次这款游戏。于是我在2025.1.3~1.5花了两天时间，纯净无作弊单人极限生存通关了这款游戏。
###### 之前我认为这样的游戏是无聊的。因为我总是和朋友一起联机，总是会加入很多辅助性的、大大降低游戏难度的MOD。在我通关一次后，我明白了这个游戏也是会有很多乐趣的。沙盒游戏的开放程度很高，自由度也很高，如果我能按自己的想法创造游戏机制、游戏内容，那该有多幸福！
###### MC社区能发展到今天，离不开MOD的帮助。我小时候也有自己学习写MOD的想法，只不过那会什么都不会。现在刚刚学习了JavaWeb的一套内容，Java语言也用得相对来说比较熟练了，突发奇想，就进入了模组的学习。
###### 希望自己能坚持下去吧。既然做了，就做出成果，做出可以供游玩的MOD出来。共勉。 于2025.1.11

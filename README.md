修改日志
===
<br>
2015-10-21
---
<br>
Bug编号：BUG1<br>
BUG描述：我的资料页面选取所在城市，默认北京情况下从县切换到市辖区，程序崩溃<br>
修复人：李超<br>
<br>
2015-10-22
---
<br>
Bug编号：BUG2<br>
Bug描述：在有网络链接的情况下进入注册页面，然后此时切断所有网络连接，点击获取验证码<br>
会因为没有网络获取不到json数据产生nullpointer<br>
修复人：李超<br>
<br>
需求编号:XQ1<br>
需求描述：需要判断是否可以点击放款按钮，<br>
后台通过添加一个新字段<br>
( "agree_fangkuan": "0" //是否放款， 0：不放，1：放)来进行判断<br>
<br>
Bug编号：BUG3<br>
Bug描述：在有网络链接的情况下进入科技金融→右上角“行”按钮页面，然后此时切断所有网络连接，点击社区<br>
会因为没有网络获取不到json数据产生nullpointer<br>
修复人：李超<br>
<br>
2015-10-23
---
 Bug编号：BUG4<br>
 Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer<br>
 修复人：李超<br>
<br>
Bug编号：BUG5<br>
Bug描述：未判断数据为空时候的情况，导致空指针<br>
修复人：李超<br>
<br>
Bug编号：BUG6<br>
Bug描述：在主页面和个人资料页面频繁切换后会内存溢出，该版App还有很多内存管理缺陷待解决<br>
修复人：李超<br>
<br>
Bug编号：BUG7<br>
Bug描述：原代码采用keydown方式，未考虑到一直按住不松开的情况，这里改为keyup<br>
修复人：李超<br>
<br>
Bug编号：BUG8<br>
Bug描述：未能考虑到该对象为null的情况，可能会导致空指针<br>
修复人：李超<br>
<br>
Bug编号：Bug9<br>
Bug描述：导航页下面有白边，加了这个意义何在？0dp<br>
修复人：李超<br>
<br>
版本号更新为1.0.1
===
2015-11-23
---
<br>
需求编号:XQ2<br>
需求描述：取消主页新三板<br>
修复人：李超<br>
<br>
版本号更新为1.0.2
===
<br>
需求编号:XQ3<br>
需求描述：优化主页热门项目加载速度<br>
修复人：李超<br>
<br>
<br>
Bug编号：Bug10<br>
Bug描述：首页信息下拉请求后重复<br>
修复人：李超<br>
<br>
2015-11-30
---
<br>
Bug编号：Bug11<br>
Bug描述：项目众筹中和预热时间为0时，认购和约谈仍可点击<br>
修复人：李超<br>
<br>
2015-12-02
---
<br>
Bug编号：Bug12<br>
Bug描述：点击首页科技进入的页面，下拉无反应<br>
修复人：李超<br>
<br>
版本号更新为1.0.3
===
<br>
2015-12-10
---
需求编号:XQ4
需求描述：优化导航页特效，使用开源库JazzViewPager
修复人：李超

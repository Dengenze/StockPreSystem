package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.Collection;
import Dto.Stock;
import Dto.usertostock;
import cloud.DengSequrity;
import cloud.user.Service.MP.UserServiceForCollection;
import cloud.user.Service.MP.UserServiceForUserToStock;

import cloud.user.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CollectionController {
    @Resource
    UserServiceForCollection ServiceForCollection;
    @Resource
    UserServiceForUserToStock ServiceForUserToStock;

    @Resource
    UserService userService;

    //所有的UserToStock逻辑上应该是CollectionToStock，但是数据库实在不想改了

    //用户添加收藏夹
    @PostMapping("user/addCollectionGroup")
    public CommonResponse<String> addCollectionGroup(HttpServletRequest request,
                                                     @RequestParam("userid") int userid,
                                                     @RequestParam("collectionname") String collectionname
                                                     )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }
        if(!ServiceForCollection.lambdaQuery().eq(Collection::getCollectionname,collectionname).list().isEmpty())
        {
            return new CommonResponse<String>(400,"收藏夹名称重复",null,null);
        }
        Collection collection = new Collection();
        collection.setUserid(userid).setCollectionname(collectionname);
        boolean b = ServiceForCollection.saveOrUpdate(collection);
        if (b)
        {
            return new CommonResponse<String>(200,"插入成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"插入失败",null,null);
        }
    }
    //用户查询收藏夹
    @PostMapping("user/selectCollectionGroup")
    public CommonResponse<List<Collection>> selectCollectionGroup(HttpServletRequest request,
                                                                  @RequestParam("userid") int userid,
                                                                  @RequestParam("collectionname") String collectionname
    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<Collection>>(402,"权限不足",null,null);
        }
        if(collectionname!=null)
        {
            List<Collection> list = ServiceForCollection.lambdaQuery().like(Collection::getCollectionname, collectionname).eq(Collection::getUserid,userid).list();
            return new CommonResponse<>(200,"查询成功",list,null);
        }
        else
        {
            List<Collection> list = ServiceForCollection.lambdaQuery().eq(Collection::getUserid,userid).list();
            return new CommonResponse<>(200,"查询成功",list,null);
        }
    }
    //用户修改收藏夹名字
    @PostMapping("user/changeCollectionGroupName")
    public CommonResponse<String> changeCollectionGroupName(HttpServletRequest request,
                                                            @RequestParam("collectionid") int collectionid,
                                                            @RequestParam("collectionname") String collectionname)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }

        boolean ifupdate = ServiceForCollection.lambdaUpdate().eq(Collection::getCollectionid, collectionid).set(Collection::getCollectionname, collectionname).update();

        if(ifupdate)
        {
            return new CommonResponse<String>(200,"修改成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"修改失败",null,null);
        }
    }

    //删除收藏夹
    @PostMapping("user/deleteCollection")
    public CommonResponse<String> deleteCollection(HttpServletRequest request,
                                                   @RequestParam("collectionid") int collectionid)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }
        boolean b = ServiceForCollection.lambdaUpdate().eq(Collection::getCollectionid, collectionid).remove();
        if(b)
        {
            return new CommonResponse<String>(200,"删除成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"删除失败",null,null);
        }
    }
    //用户向某个收藏里添加股票
    @PostMapping("user/addStockToCollection")
    public CommonResponse<String> addStockToCollection(HttpServletRequest request,
                                                   @RequestParam("collectionid") int collectionid,
                                                       @RequestParam("symbol") String symbol
                                                       )
    {
//        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }
        List<Collection> list = ServiceForCollection.lambdaQuery().eq(Collection::getCollectionid, collectionid).list();
        //如果没有这个collection
        if(list==null)
        {
            return new CommonResponse<String>(400,"添加失败",null,null);
        }

        if(!ServiceForUserToStock.lambdaQuery().eq(usertostock::getSymbol,symbol).list().isEmpty())
        {
            return new CommonResponse<String>(400,"不能重复添加收藏",null,null);
        }
        usertostock userToStock = new usertostock();
        userToStock.setCollectionid(collectionid).setSymbol(symbol);
        boolean b = ServiceForUserToStock.save(userToStock);
        if(b)
        {
            return new CommonResponse<String>(200,"添加成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"添加失败",null,null);
        }
    }

    //用户从某个收藏夹里删除股票
    @PostMapping("user/deleteFromCollection")
    public CommonResponse<String> deleteFromCollection(HttpServletRequest request,
                                                       @RequestParam("collectionid") int collectionid,
                                                       @RequestParam("symbol") String symbol
    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }

        boolean b = ServiceForUserToStock.lambdaUpdate().eq(usertostock::getCollectionid, collectionid).eq(usertostock::getSymbol,symbol).remove();
        if(b)
        {
            return new CommonResponse<String>(200,"删除成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"删除失败",null,null);
        }
    }

    //查询某收藏夹里全部的股票
    @PostMapping("user/selectStockFromCollection")
    public CommonResponse<List<Stock>> selectStockFromCollection(HttpServletRequest request,
                                                                 @RequestParam("collectionid") int collectionid
    )
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<Stock>>(402,"权限不足",null,null);
        }

        List<Stock> stocks = userService.selectStockFromCollection(collectionid);
        return new CommonResponse<>(200,"查询成功",stocks,null);
    }
}

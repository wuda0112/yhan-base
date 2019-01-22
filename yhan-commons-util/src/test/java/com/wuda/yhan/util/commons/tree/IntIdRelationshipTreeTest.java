package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

public class IntIdRelationshipTreeTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        IntIdRelationshipTree<IntIdPidElement> company = genOrganizationStructure(); // 生成公司组织架构
        display(company); // 遍历并且打印
    }

    /**
     * 生成公司组织架构.
     *
     * @return 树形结构的组织架构
     */
    private IntIdRelationshipTree<IntIdPidElement> genOrganizationStructure() {

        IntIdRelationshipTree<IntIdPidElement> tree = new IntIdRelationshipTree<>(Integer.MIN_VALUE);

        RelationshipTree.Node<IntIdPidElement> china_node = tree.updateOrCreateNode(china);

        RelationshipTree.Node<IntIdPidElement> hunan_province_node = tree.updateOrCreateNode(hunan_province);
        RelationshipTree.Node<IntIdPidElement> zhangjiajie_node = tree.updateOrCreateNode(zhangjj);
        RelationshipTree.Node<IntIdPidElement> changsha_node = tree.updateOrCreateNode(changsha);
        RelationshipTree.Node<IntIdPidElement> sangzhi_node = tree.updateOrCreateNode(sangzhi);


        RelationshipTree.Node<IntIdPidElement> guangdong_province_node = tree.updateOrCreateNode(guangdong_province);
        RelationshipTree.Node<IntIdPidElement> guangzhou_node = tree.updateOrCreateNode(guangzhou);
        RelationshipTree.Node<IntIdPidElement> tianhe_node = tree.updateOrCreateNode(tianhe);
        RelationshipTree.Node<IntIdPidElement> dongguan_node = tree.updateOrCreateNode(dongguan);

        RelationshipTree.Node<IntIdPidElement> hubei_province_node = tree.updateOrCreateNode(hubei_province);
        RelationshipTree.Node<IntIdPidElement> yunan_province_node = tree.updateOrCreateNode(yunan_province);

        tree.createRelationship(tree.getRoot(), china_node);
        tree.createRelationship(china_node, hunan_province_node);
        tree.createRelationship(china_node, guangdong_province_node);
        tree.createRelationship(china_node, hubei_province_node);
        tree.createRelationship(china_node, yunan_province_node);

        tree.createRelationship(hunan_province_node, zhangjiajie_node);
        tree.createRelationship(hunan_province_node, changsha_node);

        tree.createRelationship(zhangjiajie_node, sangzhi_node);

        tree.createRelationship(guangdong_province_node, guangzhou_node);
        tree.createRelationship(guangzhou_node, tianhe_node);
        tree.createRelationship(guangdong_province_node, dongguan_node);

        return tree;
    }

}

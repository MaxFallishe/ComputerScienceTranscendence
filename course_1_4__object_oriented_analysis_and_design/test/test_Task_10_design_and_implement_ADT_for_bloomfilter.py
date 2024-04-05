import unittest
from course_1_4__object_oriented_analysis_and_design.Task_10_design_and_implement_ADT_for_bloomfilter import BloomFilter


class TestBloomFilterAddOperation(unittest.TestCase):
    def test_add_operation__1(self):
        bfilter = BloomFilter()
        bfilter.add('sdf')
        result = bfilter.is_value('sdf')
        self.assertTrue(result)

    def test_add_operation__2(self):
        bfilter = BloomFilter()
        bfilter.add('sdf')
        bfilter.add('sdf')
        bfilter.add('sdf')
        result = bfilter.is_value('sdf')
        self.assertTrue(result)

    def test_add_operation__3(self):
        bfilter = BloomFilter()
        bfilter.add('sdf')
        bfilter.add('sss')
        bfilter.add('ssd')
        result = bfilter.is_value('ssd')
        self.assertTrue(result)

    def test_add_operation__4(self):
        bfilter = BloomFilter()
        bfilter.add('sdf')
        bfilter.add('sss')
        bfilter.add('ssd')
        result = bfilter.is_value('ssf')
        self.assertFalse(result)

    def test_add_operation__5(self):
        bfilter = BloomFilter()
        bfilter.add('sdf')
        bfilter.add('sss')
        bfilter.add('ssd')
        bfilter.add('sgg')
        result = True
        for i in ['sdf', 'sss', 'ssd', 'sgg']:
            result &= bfilter.is_value(i)
        self.assertTrue(result)

    def test_add_operation__6(self):
        bfilter = BloomFilter()
        bfilter.add('sdf1')
        bfilter.add('sss2')
        bfilter.add('ssd3')
        bfilter.add('sg4')
        bfilter.add('sgfgh5')
        bfilter.add('sggfhg6')
        bfilter.add('sgkg7')
        bfilter.add('sag8')
        bfilter.add('sgs9')
        bfilter.add('sdfg10')
        result = bfilter.is_value('sdfg10')
        self.assertTrue(result)

    def test_add_operation__7(self):
        bfilter = BloomFilter()
        bfilter.add('sdf1')
        bfilter.add('sss2')
        bfilter.add('ssd3')
        bfilter.add('sg4')
        bfilter.add('sgfgh5')
        bfilter.add('sggfhg6')
        bfilter.add('sgkg7')
        bfilter.add('sag8')
        bfilter.add('sgs9')
        bfilter.add('sdfg10')
        bfilter.add('gggaw11')
        result = bfilter.is_value('gggaw11')
        self.assertFalse(result)

    def test_add_operation__8(self):
        bfilter = BloomFilter()
        bfilter.add('sdf1')
        result = bfilter.get_add_status()
        self.assertEqual(result, bfilter.ADD_OK)

    def test_add_operation__9(self):
        bfilter = BloomFilter()
        result = bfilter.get_add_status()
        self.assertEqual(result, bfilter.ADD_NIL)

    def test_add_operation__10(self):
        bfilter = BloomFilter()
        bfilter.add('sdf1')
        bfilter.add('sss2')
        bfilter.add('ssd3')
        bfilter.add('sg4')
        bfilter.add('sgfgh5')
        bfilter.add('sggfhg6')
        bfilter.add('sgkg7')
        bfilter.add('sag8')
        bfilter.add('sgs9')
        bfilter.add('sdfg10')
        bfilter.add('gggaw11')
        result = bfilter.get_add_status()
        self.assertEqual(result, bfilter.ADD_ERR)

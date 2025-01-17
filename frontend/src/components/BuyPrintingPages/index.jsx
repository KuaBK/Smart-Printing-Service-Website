import React, { useState, useEffect, useContext } from 'react';
import './stylemodule.css';
import momo from './img/momo.png';
import bk from './img/bk.jpg';
import api from '../../Services/api';
import { GlobalContext } from '../../Context';

export const BuyPrintingPages = () => {
  const [soTrang, setSoTrang] = useState(100); // Default to 100 pages
  const [tongTienGiay, setTongTienGiay] = useState(0);
  const [giamGia, setGiamGia] = useState(0); // Fixed discount
  const [tongThanhToan, setTongThanhToan] = useState(0);
  const [showPaymentModal, setShowPaymentModal] = useState(false); // Control modal visibility
  const [valueDiscount, setValueDiscount] = useState('');
  const {profile} =useContext(GlobalContext);

  // Calculate total price when `soTrang` changes
  useEffect(() => {
    let giaTrang = 500; // Price per page: 1000 vnd
    let tongTienGiay = soTrang * giaTrang;
    setTongTienGiay(tongTienGiay);
    setTongThanhToan(tongTienGiay - giamGia >= 0 ? tongTienGiay - giamGia : 0); // Total after discount
  }, [soTrang, giamGia]);

  // Handle button click to set number of pages
  const handleOptionClick = (value) => {
    setSoTrang(parseInt(value));
  };

  // Handle input change for the number of pages
  const handleInputChange = (e) => {
    const value = e.target.value ? parseInt(e.target.value) : 0;
    setSoTrang(value);
  };

  const handleInputDiscount = async (e) => {
    const code  = e.target.value.toUpperCase();
    setValueDiscount(code);
    try {
      const response = await api.get(`/payment/discount?code=${code}`);
      if (response.status === 200) {
        setGiamGia(response.data.data.pagesFree * 500);
        console.log(response.data.data.pagesFree);
      } else if (response.status === 400) {
        setGiamGia(0);
        setValueDiscount('');

      }
    } catch (error) {
      setGiamGia(0);
    }
  }

  // Handle "Thanh toán" button to show modal
  const handleThanhToan = () => {
    setShowPaymentModal(true);
  };

  const handlePaymentVNPay = async () => {
    try {
      const response = await api.post('/payment/vnpay', {
        "page": soTrang,
        "discountCode": giamGia === 0 ? '' : valueDiscount
      });
      console.log(response);
      if (response.status === 200) {
        window.open(response.data.url, '_blank').focus();
        setShowPaymentModal(false);
        setSoTrang(0);
        setGiamGia(0);
        setValueDiscount('');
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Close modal when "Hủy" button is clicked or outside is clicked
  const handleCloseModal = () => {
    setShowPaymentModal(false);
  };

  return (
    <div>
      {/* Main Content */}
      <div className="hello">
        <div className="name">Hi, {profile?.name}</div>
        <p className="text">Hope you have a good day</p>
      </div>

      <div className="buying">
        <div className="form-group">
          <p className="so-trang">Số trang in cần mua</p>
          <input
            type="number"
            id="so-trang"
            placeholder="100 tờ"
            value={soTrang}
            onChange={handleInputChange}
            max="1000"
          />
          <div className="have-section">
            <p className="text having">Số trang in đang có</p>
            <p className="number text">100 tờ</p>
          </div>
        </div>

        <div className="form-group">
          <div className="options">
            <button className="option-btn" onClick={() => handleOptionClick(20)}>
              20 tờ
            </button>
            <button className="option-btn" onClick={() => handleOptionClick(50)}>
              50 tờ
            </button>
            <button className="option-btn" onClick={() => handleOptionClick(100)}>
              100 tờ
            </button>
            <button className="option-btn" onClick={() => handleOptionClick(200)}>
              200 tờ
            </button>
          </div>
        </div>

        <div className="form-group">
          <p className="ma-giam-gia">Mã giảm giá</p>
          <input type="text" id="ma-giam-gia" placeholder="Nhập mã giảm giá" onChange={handleInputDiscount}/>
        </div>

        <div className="summary">
          <p>Tổng tiền giấy: <span id="tong-tien-giay">{tongTienGiay} vnd</span></p>
          <p>Giảm giá: <span id="giam-gia">{giamGia} vnd</span></p>
          <p>Tổng tiền thanh toán: <span id="tong-thanh-toan">{tongThanhToan} vnd</span></p>
        </div>

        <button className="btn-thanh-toan" onClick={handleThanhToan}>Thanh toán</button>
      </div>

      {/* Payment Modal */}
      {showPaymentModal && (
        <div className="modal-overlay" onClick={handleCloseModal}>
          <div className="modal-content relative" onClick={(e) => e.stopPropagation()}>
            <h2 className='font-bold'>Phương thức thanh toán</h2>
            <div className='flex flex-col'>
            <button className="payment-option" onClick={handlePaymentVNPay}>
              <img src={momo} alt="Momo"/>
              VNPAY
            </button>
            <button className="payment-option">
              <img src={bk} alt="BK Pay" />
              BK Pay
            </button>
            </div>
            <button className="top-[10px] right-[20px] w-[40px] h-[10px] rounded-[10px] bg-[#FF5C5C] absolute" onClick={handleCloseModal}/>
          </div>
        </div>
      )}
    </div>
  );
};

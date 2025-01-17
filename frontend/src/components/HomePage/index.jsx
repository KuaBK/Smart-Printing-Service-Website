import React, {useState, useEffect} from 'react'
import classes from './style.module.css'
import logo from '../../assets/logo.png'
import play from '../../assets/play.svg'
import img1 from '../../assets/hcmut_bg.png'
import api from '../../Services/api'
import {ManagePrint} from '../ManagePrint'

export const HomePage = () => {
  const [showConfig, setShowConfig] = useState(true);
  const [fileReport, setFileReport] = useState({});
  const [printers, setPrinters] = useState([]);
  const [fileConfig, setFileConfig] = useState({});
  const handlePrint = async () => {
    const code = document.getElementById('code').value;
    try {
      const response = await api.get(`/file-configs/public/code?code=${code}`);
      const fileReport = response.data;
      // console.log(fileReport);
      setFileReport(fileReport.data.fileConfig.document);
      setShowConfig(false);
      setPrinters(fileReport.data.printers);
      setFileConfig(fileReport.data.fileConfig);
      console.log(fileConfig);
      // console.log(fileReport.data.fileConfig.document);
      // Handle the file report as needed
    } catch (error) {
      console.error('Error fetching the file report:', error);
    }
  };
  useEffect(() => {
    console.log(fileReport);
  }, [fileReport]);
  return (
    <div className="lg:w-[100vw] mx-auto font-semibold relative bg-cover bg-[url(${img1})] bg-no-repeat	bg-fixed bg-center">
      <div className={classes.header_border}>
      </div>
      <div className='bg-opacity-10' style={{background: `url(${img1})`, backgroundSize: "cover"}}>
      <div className='flex flex-row justify-between h-[70px] items-center px-[5%] '>
        <div className='flex flex-row justify-start items-center'>
          <img src={logo} className='p-[5px] w-[50px] h-[50px] py-[10px]'/>
          <div className=' font-semibold'>
            HCMUT-SPSS
          </div>    
        </div>
        <div>
          <ul className="flex flex-row min-w-[230px] justify-between font-semibold cursor-pointer">
            <li className='hover:text-[#0f6cbf] font-semibold'>Home</li>
            <li className='hover:text-[#0f6cbf] font-semibold'>Hướng dẫn sử dụng</li>
          </ul>
        </div>
        <div>
        <a href='http://localhost:5173/login'><button className='w-[100px] bg-[#0f6cbf] hover:opacity-90 rounded-[60px] text-[#fff] font-semibold py-[3px]'>Login</button></a>
        </div>
      </div>

      <div className="flex flex-col w-[60vw] mt-[142px] ml-[15%]">
        <div className='font-bold py-[15px] text-[#5BACF2] text-6xl'>
          HCMUT-SSPS
        </div>
        <div className='text-lg mt-[48px] mb-[27px] w-[450px]'>
          Dịch vụ in ấn thông minh của sinh viên Trường Đại học Bách Khoa,
          Đại học Quốc gia Thành phô Hồ Chí Minh
        </div>
        <p className='text-[#384356] mb-[27px]'>Đáp ứng nhu cầu in ấn của sinh viên trong quá trình học tập tại trường.</p>

        <div className='flex flex-row mb-[27px] items-center'>
          <div className='mr-[25px]'>
          <a href='http://localhost:5173/login'><button className='w-[200px] h-[45px] bg-[#0f6cbf] hover:opacity-90 rounded-[60px] text-[#fff] font-semibold py-[5px]'>Login now</button></a>
          </div>
          <a href='#' className='flex flex-row items-center hover:opacity-90'>
          <img src={play} className='h-[22.5px] w-[22.5px] mr-[5px]'/>

          <div className='text-[#499BE9]'>
              Hướng dẫn sử dụng
          </div>
          </a>
        </div>

        <div className='flex flex-col bg-white rounded-[10px] shadow-md max-w-[500px] h-[164px] pb-[1em] px[0.5em] rounded-t-2xl mb-[120px]'>
          <div className='px-[1em] py-[15px] rounded-t-2xl font-semibold text-[#717171] bg-[#F6FAFC] mb-[30px]'>
            In tài liệu bằng mã code
          </div>
          <div className='flex flex-row items-center px-[0.75em]'>
            <form className='w-full pr-[10px]'>
              <input type="text" id="code" class=" border border-gray-300 text-sm rounded-[13px] block px-[10px] py-[13px] w-full outline-none" placeholder="Nhập mã code" required />
            </form>
            <div className='w-[100px]'>
              <button onClick={handlePrint} className='w-[100px] bg-[#0f6cbf] hover:opacity-90 rounded-[60px] text-[#fff] font-semibold py-[8px] ml-[5px] h-full'>Print</button>
              {fileReport && <ManagePrint setIsHidden={setShowConfig} isHidden={showConfig} guess={true} printer1={printers} config={fileConfig}  file={fileReport}/>}
            </div>


          </div>
        </div>
      </div>
      </div>

      <div className= 'bg-[#062251] w-100vm max-h-[270px] text-white text-sm py-[3em] px-[5em] font-bold'>
      <div className='flex flex-row justify-between h-[220px] text-white'>
        <div className='lg:flex flex-col w-[20%] hidden'>
          <img src={logo} className=' h-[90px] w-[130px]'/>  
          <div className='p-[5px] leading-[23px] text-white'>
          Trang web HCMUT-SSPS thuộc khuôn khổ môn học Công nghệ phầm mềm
           của các bạn học sinh trường đại học Bách Khoa, Đại học Quốc gia TPHCM
          </div>         
        </div>
        <div className='lg:flex-col lg:flex hidden '>
          <div className='text-xl font-bold pb-[1em] text-white'>
            Services
          </div>
          <ul>
            <li className='pb-[0.75em] text-white'>
              Customers
            </li>
            <li className='pb-[0.75em] text-white'>
              Collaboratiors
            </li>
          </ul>
        </div>

        <div className='lg:flex-col lg:flex hidden'>
          <div className='text-xl pb-[1em] font-bold text-white'>
            School
          </div>
          <ul>
            <li className='pb-[0.75em] text-white'>
              About Us
            </li>
            <li className='pb-[0.75em] text-white'>
              Our Team
            </li>
          </ul>
        </div>

        <div className='lg:flex-col lg:flex hidden'>
          <div className='text-xl pb-[1em] font-bold text-white'>
            Suppport
          </div>
          <ul>
            <li className='pb-[0.75em] text-white'>
              FAQs
            </li>
            <li className='pb-[0.75em] text-white'>
              Privacy Policy
            </li>
          </ul>
        </div>

        <div className='flex-col lg:w-[25%]'>
          <div className='text-xl pb-[1em] font-bold text-white'>
            Contact Us
          </div>
          <ul>
            <li className='pb-[0.75em] text-white'>
              Email: example@hcmut.edu.vn
            </li>
            <li className='pb-[0.75em] text-white'>
              Call: +84 1234 5678
            </li>
            <li className='pb-[0.75em] text-white'>
            Address: Trường đại học Bách khoa, 
            Đại học quốc gia TPHCM, Đông hoà, Dĩ an, Bình Dương.
            </li>
          </ul>
        </div>
      </div>

    </div>
    </div>
    
  )
}

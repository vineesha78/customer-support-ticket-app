import React,{useEffect,useRef,useState} from 'react'
import { Accordion, Button } from 'flowbite-react';
import { AuthApi } from '../../common/Apis';
function Faqs() {
    const [faqData,setFaqData] = useState([]);
    const [reload,setReload] = useState([]);
    const [newFaq,setNewFaq] = useState({
        question:"",
        answer:""
    })
    useEffect(()=>{
            getFaqData()
    },[reload])
    const handleFaqChange = (e) => {
        setNewFaq({
          ...newFaq,
          [e.target.name]: e.target.value
        });
      };
    const getFaqData = async () => {
      await AuthApi.get("/faqs").then((response)=>{
        if(response.status===200){
          setFaqData(response.data)
        }
        else{
          console.log("Error Retriving FAQ's")
        }
      })
    .catch((e)=>{
      console.log(e)
    })
  }
  const addFaq = async (e) => {
    e.preventDefault()
    await AuthApi.post("/faqs",newFaq).then((response)=>{
      if(response.status===200){
        setReload(!reload)
        console.log("FAQ Created")
        setNewFaq({
          question:"",
          answer:""
      })
      }
      else{
        console.log("Error Retriving FAQ's")
      }
    })
  .catch((e)=>{
    console.log(e)
  })
} 
const deleteFaq = async (id) => {
    await AuthApi.delete("/faqs/"+id).then((response)=>{
      if(response.status===200){
        console.log("FAQ Deleted")
        setReload(!reload)
      }
      else{
        console.log("Error Retriving FAQ's")
      }
    })
  .catch((e)=>{
    console.log(e)
  })
}
  return ( 
    <div className="w-full px-20 pt-10">
        <h1 className="my-4 text-2xl font-bold text-gray-900">
            Freqently Asked Questions (FAQ's) Editor</h1>
        <div className="m">
            <label className="block font-bold text-lg mb-1">Question</label>
            <input
              type="text"
              className="border rounded p-1 w-3/4 mb-2"
              name='question'
              value={newFaq.question}
              onChange={(e) => handleFaqChange(e)}
            />

            <label className="block font-bold text-lg mb-1">Answer</label>
            <textarea
              type="text"
              className="border rounded w-3/4 p-1"
              name='answer'
              rows={4}
              value={newFaq.answer}
              onChange={(e) => handleFaqChange(e)}
            />
        </div>
        <button onClick={(e)=>{addFaq(e)}} className="inline-flex mt-5 mb-20 items-center rounded-lg bg-maroon px-4 py-2 text-center text-sm font-medium text-white hover:bg-indigo-800 focus:outline-none focus:ring-4 focus:ring-indigo-300 dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
        Add</button>
            {faqData.map((faq)=>
            <div className="flex justify-center">
            <Accordion collapseAll className="w-3/4">
            <Accordion.Panel className="w-full">
            <Accordion.Title>
                {faq.question}
            </Accordion.Title>
            <Accordion.Content>
                {faq.answer}
            </Accordion.Content>
            </Accordion.Panel>
            </Accordion>
            <div onClick={()=>{deleteFaq(faq.id)}} className="ml-3"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="red" className="w-6 h-6"><path fillRule="evenodd" d="M5.47 5.47a.75.75 0 011.06 0L12 10.94l5.47-5.47a.75.75 0 111.06 1.06L13.06 12l5.47 5.47a.75.75 0 11-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 01-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 010-1.06z" clipRule="evenodd" /></svg></div>
            </div>
            )}
    </div>
  )
}


export default Faqs